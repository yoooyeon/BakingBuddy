package com.coco.bakingbuddy.follow.controller;

import com.coco.bakingbuddy.follow.dto.response.FollowResponseDto;
import com.coco.bakingbuddy.follow.service.FollowService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/follow")
@RequiredArgsConstructor
@RestController
public class FollowQueryController {
    private final FollowService followService;

    @GetMapping("followed-users")
    public ResponseEntity<SuccessResponse<List<FollowResponseDto>>>
    selectFollowedUsers(@AuthenticationPrincipal User user) {
        return SuccessResponse.toResponseEntity(followService.getAllFollowedUsersDto(user));
    }

    @GetMapping("followers")
    public ResponseEntity<SuccessResponse<List<FollowResponseDto>>>
    selectFollowers(@AuthenticationPrincipal User user) {
        return SuccessResponse.toResponseEntity(followService.getAllFollowersDto(user));
    }
}
