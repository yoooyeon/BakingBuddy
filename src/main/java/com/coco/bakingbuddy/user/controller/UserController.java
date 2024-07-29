package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.DirectoryWithRecipesResponseDto;
import com.coco.bakingbuddy.search.dto.response.RecentSearchResponseDto;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.SelectUserProfileResponseDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static com.coco.bakingbuddy.user.dto.response.SelectUserProfileResponseDto.fromEntity;

@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectUserResponseDto>>>
    allUsers() {
        List<SelectUserResponseDto> users = userService.selectAll();
        return toResponseEntity("모든 유저 조회 성공", users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>>
    selectByUserId(@PathVariable("userId") Long userId) {
        User user = userService.selectById(userId);
        return toResponseEntity("유저 프로필 정보 조회 성공", fromEntity(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>>
    editUser(@PathVariable("userId") Long userId,
             @RequestPart("username") String username,
             @RequestPart("nickname") String nickname,
             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        return toResponseEntity("유저 정보 수정 성공",
                fromEntity(userService.editUserInfo(userId, username, nickname, profileImage)));
    }

    @GetMapping("recent")
    public ResponseEntity<SuccessResponse<List<RecentSearchResponseDto>>>
    recent(@AuthenticationPrincipal User user) {
        return toResponseEntity("최근 검색어 조회 성공"
                , userService.findRecentSearchesByUserId(user.getId()));
    }
    @GetMapping("{userId}/recipes")
    public ResponseEntity<SuccessResponse<List<DirectoryWithRecipesResponseDto>>>
    selectRecipeByUserId(@PathVariable("userId") Long userId) {
        return toResponseEntity("유저 아이디로 레시피 조회 성공", userService.selectDirsByUserId(userId));
    }
}
