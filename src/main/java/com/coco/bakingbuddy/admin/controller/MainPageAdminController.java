package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.service.MainRecipeService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/main")
public class MainPageAdminController {
    private final MainRecipeService mainRecipeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<String>>
    registerMainRecipe(@RequestParam Long recipeId) {
        mainRecipeService.registerMainRecipe(recipeId);
        return toResponseEntity("메인 레시피 등록 성공", "레시피 ID: " + recipeId);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>>
    selectMainRecipeCandidates() {
        return toResponseEntity("메인 레시피 후보 조회"
                , mainRecipeService.selectMainRecipeCandidates());
    }
}
