package com.coco.bakingbuddy.follow.service;

import com.coco.bakingbuddy.follow.domain.Follow;
import com.coco.bakingbuddy.follow.dto.response.FollowResponseDto;
import com.coco.bakingbuddy.follow.dto.response.FollowSummaryResponseDto;
import com.coco.bakingbuddy.follow.repository.FollowQueryDslRepository;
import com.coco.bakingbuddy.follow.repository.FollowRepository;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;
import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowQueryDslRepository followQueryDslRepository;

    @Transactional
    public void follow(Long followerId, UUID followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        if (follower.equals(followed)) {
            throw new CustomException(CANNOT_FOLLOW_SELF);
        }
        followRepository.save(Follow.builder().followed(followed).follower(follower).build());
    }

    @Transactional
    public void unFollow(Long followerId, UUID followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        long removedRowCount = followQueryDslRepository.unFollow(follower, followed);
    }

    @Transactional(readOnly = true)
    public boolean isFollowing(User follower, UUID followedId) {
        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return followQueryDslRepository.existsByFollowerAndFollowed(follower, followed);
    }

    @Transactional(readOnly = true)
    public List<FollowResponseDto> getAllFollowedUsersDto(User user) {
        List<FollowResponseDto> result = new ArrayList<>();
        List<Follow> follows = followRepository.findByFollower(user);
        for (Follow follow : follows) {
            User followed = follow.getFollowed();
            result.add(FollowResponseDto.builder()
                    .username(followed.getUsername())
                    .uuid(followed.getUuid())
                    .profileImageUrl(followed.getProfileImageUrl())
                    .build());
        }
        return result;
    }

    public List<FollowResponseDto> getAllFollowers(User user) {
        List<FollowResponseDto> result = new ArrayList<>();
        List<Follow> follows = followRepository.findByFollowed(user);
        for (Follow follow : follows) {
            User followed = follow.getFollower();
            result.add(FollowResponseDto.builder()
                    .username(followed.getUsername())
                    .uuid(followed.getUuid())
                    .build());
        }
        return result;
    }

    public FollowSummaryResponseDto getSummary(User user) {
        List<Follow> followers = followRepository.findByFollowed(user);
        List<Follow> followedUsers = followRepository.findByFollower(user);

        List<FollowResponseDto> followerDtos = followers.stream()
                .map(f -> new FollowResponseDto(f.getFollower().getUsername(), f.getFollower().getUuid(), f.getFollower().getProfileImageUrl()))
                .collect(Collectors.toList());

        List<FollowResponseDto> followedUserDtos = followedUsers.stream()
                .map(f -> new FollowResponseDto(f.getFollowed().getUsername(), f.getFollowed().getUuid(), f.getFollowed().getProfileImageUrl()))
                .collect(Collectors.toList());

        return FollowSummaryResponseDto.builder()
                .followerCnt(followers.size())
                .followedCnt(followedUsers.size())
                .followers(followerDtos)
                .followedUsers(followedUserDtos)
                .build();
    }
    @Transactional(readOnly = true)
    public List<User> getAllFollowedUsers(User user) {
        List<Follow> follows = followRepository.findByFollower(user);
        return follows.stream().map(x->x.getFollowed()).collect(Collectors.toList());
    }
}
