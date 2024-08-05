package com.coco.bakingbuddy.follow.controller;

import com.coco.bakingbuddy.follow.service.FollowService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.global.response.ErrorResponse;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
            return SuccessResponse.toResponseEntity("팔로우 성공");
        } catch (CustomException e) {
            return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, "팔로우 실패" + e.getMessage());
        }
    }

    @PostMapping("/unfollow")
    public ResponseEntity<? extends Object> unFollowUser(
            @RequestParam("followedId") UUID followedId,
            @AuthenticationPrincipal User user) {
        try {
            followService.unFollow(user.getId(), followedId);
            return SuccessResponse.toResponseEntity("언팔로우 성공");
        } catch (CustomException e) {
            return ErrorResponse.toResponseEntity(HttpStatus.NOT_FOUND, "언팔로우 실패" + e.getMessage());

        }
    }

    @GetMapping("/is-following")
    public ResponseEntity<SuccessResponse<String>> isFollowing(
            @RequestParam("followedId") UUID followedId,
            @AuthenticationPrincipal User user) {
        boolean isFollowing = followService.isFollowing(user, followedId);
        return SuccessResponse.toResponseEntity(isFollowing ? "Following" : "Not following");
    }
}
