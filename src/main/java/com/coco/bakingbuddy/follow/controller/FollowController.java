package com.coco.bakingbuddy.follow.controller;

import com.coco.bakingbuddy.follow.dto.response.FollowSummaryResponseDto;
import com.coco.bakingbuddy.follow.service.FollowService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/follow")
public class FollowController {

    private final FollowService followService;

    @PostMapping("/follow")
    public ResponseEntity<? extends Object> followUser(
            @RequestParam("followedId") UUID followedId,
            @AuthenticationPrincipal User user) {
        try {
            followService.follow(user.getId(), followedId);
            return toResponseEntity("팔로우 성공");
        } catch (CustomException e) {
            return toResponseEntity(e.getCode().getStatus(), e.getMessage());
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<? extends Object> unFollowUser(
            @RequestParam("followedId") UUID followedId,
            @AuthenticationPrincipal User user) {
        try {
            followService.unFollow(user.getId(), followedId);
            return toResponseEntity("언팔로우 성공");
        } catch (CustomException e) {
            return toResponseEntity(e.getCode().getStatus(), e.getMessage());
        }
    }

    @GetMapping("/is-following")
    public ResponseEntity<SuccessResponse<String>> isFollowing(
            @RequestParam("followedId") UUID followedId,
            @AuthenticationPrincipal User user) {
        boolean isFollowing = followService.isFollowing(user, followedId);
        return toResponseEntity(isFollowing ? "Following" : "Not following");
    }

    @GetMapping("/summary")
    public ResponseEntity<SuccessResponse<FollowSummaryResponseDto>>
    summary(@AuthenticationPrincipal User user) {
        return toResponseEntity("팔로우 요약 조회 성공", followService.getSummary(user));
    }
}
