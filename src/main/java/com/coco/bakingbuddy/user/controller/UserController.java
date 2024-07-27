package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.SelectUserProfileResponseDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
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
    private final RecipeService recipeService;

    private final ApplicationEventPublisher eventPublisher;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectUserResponseDto>>> allUsers() {
        List<SelectUserResponseDto> users = userService.selectAll();
        return toResponseEntity("모든 유저 조회 성공", users);
    }

    @GetMapping("{userId}")
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>> selectByUserId(@PathVariable("userId") Long userId) {
        User user = userService.selectById(userId);
//        List<DirectoryWithRecipesResponseDto> dirs = recipeService.selectDirsByUserId(userId);
//        Map<String, Object> responseData = new HashMap<>();
//        responseData.put("user", user);
//        responseData.put("dirs", dirs);
        return toResponseEntity("유저 아이디로 디렉토리 조회 성공", fromEntity(user));
    }

    @PutMapping("{userId}")
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>> editUser(
            @PathVariable("userId") Long userId,
            @RequestPart("username") String username,
            @RequestPart("nickname") String nickname,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        SelectUserProfileResponseDto profile = fromEntity(userService.editUserInfo(userId, username, nickname, profileImage));
        return toResponseEntity("유저 정보 수정 성공", profile);
    }


}
