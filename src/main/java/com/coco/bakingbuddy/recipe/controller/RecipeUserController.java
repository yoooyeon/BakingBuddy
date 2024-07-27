package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.DirectoryWithRecipesResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@RestController
public class RecipeUserController {
    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping("users/{userId}")
    public ResponseEntity<SuccessResponse<Map<String, Object>>> selectRecipeByUserId(
            @PathVariable("userId") Long userId) {
        Map<String, Object> responseData = new HashMap<>();
        User user = userService.selectById(userId);
        List<DirectoryWithRecipesResponseDto> dto = recipeService.selectDirsByUserId(userId);
        responseData.put("user", user);
        responseData.put("recipes", dto);
        return toResponseEntity("유저 아이디로 레시피 조회 성공", responseData);
    }
}
