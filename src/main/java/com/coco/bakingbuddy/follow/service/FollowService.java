package com.coco.bakingbuddy.follow.service;

import com.coco.bakingbuddy.follow.domain.Follow;
import com.coco.bakingbuddy.follow.repository.FollowQueryDslRepository;
import com.coco.bakingbuddy.follow.repository.FollowRepository;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;

@RequiredArgsConstructor
@Slf4j
@Service
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final FollowQueryDslRepository followQueryDslRepository;

    public void follow(Long followerId, UUID followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        followRepository.save(Follow.builder().followed(followed).follower(follower).build());
    }

    public void unFollow(Long followerId, UUID followedId) {
        User follower = userRepository.findById(followerId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        long removedRowCount = followQueryDslRepository.unFollow(follower, followed);
    }

    public boolean isFollowing(User follower, UUID followedId) {
        User followed = userRepository.findByUuid(followedId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return followQueryDslRepository.existsByFollowerAndFollowed(follower, followed);
    }
}
