package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DirectoryWithRecipesResponseDto;
import com.coco.bakingbuddy.search.dto.response.RecentSearchResponseDto;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.SelectUserIntroResponseDto;
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
import java.util.UUID;

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

    /**
     * 유저 프로필 조회 (내 프로필)
     *
     * @param user
     * @return
     */
    @GetMapping("mypage")
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>>
    selectByUserId(@AuthenticationPrincipal User user) {
        return toResponseEntity("유저 프로필 정보 조회 성공", fromEntity(userService.selectById(user.getId())));
    }

    /**
     * 유저 프로필 수정 (내 프로필 수정)
     *
     * @param user
     * @param username
     * @param nickname
     * @param introduction
     * @param profileImage
     * @return
     */
    @PutMapping
    public ResponseEntity<SuccessResponse<SelectUserProfileResponseDto>>
    editUser(@AuthenticationPrincipal User user,
             @RequestPart("username") String username,
             @RequestPart("nickname") String nickname,
             @RequestPart("introduction") String introduction,
             @RequestPart(value = "profileImage", required = false) MultipartFile profileImage) {
        return toResponseEntity("유저 정보 수정 성공",
                fromEntity(userService.editUserInfo(user.getId(), username, nickname, introduction, profileImage)));
    }

    /**
     * 유저의 최근 검색어 조회
     *
     * @param user
     * @return
     */
    @GetMapping("recent")
    public ResponseEntity<SuccessResponse<List<RecentSearchResponseDto>>>
    recent(@AuthenticationPrincipal User user) {
        return toResponseEntity("최근 검색어 조회 성공"
                , userService.findRecentSearchesByUserId(user.getId()));
    }

    /**
     * 유저의 레시피 조회
     *
     * @param user
     * @return
     */
    @GetMapping("recipes")
    public ResponseEntity<SuccessResponse<List<DirectoryWithRecipesResponseDto>>>
    selectRecipeByUserId(@AuthenticationPrincipal User user) {
        return toResponseEntity("유저 아이디로 레시피 조회 성공", userService.selectRecipesGroupbyDirByUserId(user.getId()));
    }

    /**
     * 다른 사람에게 공개하는 유저의 프로필
     *
     * @param uuid
     * @return
     */
    @GetMapping("intro/{uuid}")
    public ResponseEntity<SuccessResponse<SelectUserIntroResponseDto>>
    introUser(@PathVariable("uuid") UUID uuid) {
        return toResponseEntity("유저 소개 프로필 조회 성공", userService.selectIntroByUuid(uuid));
    }

    /**
     * 유저의 상품 조회
     *
     * @param user
     * @return
     */
    @GetMapping("products")
    public ResponseEntity<SuccessResponse<List<SelectProductResponseDto>>>
    selectProductsByUserId(@AuthenticationPrincipal User user) {
        return toResponseEntity("유저 아이디로 등록한 상품 조회 성공", userService.selectProductsByUserId(user.getId()));
    }
}
