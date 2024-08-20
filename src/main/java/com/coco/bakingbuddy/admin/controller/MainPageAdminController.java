package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.dto.response.SelectMainRecipeResponseDto;
import com.coco.bakingbuddy.admin.service.MainRecipeService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
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

    @PostMapping("{id}/recipes/{recipeId}")
    public ResponseEntity<SuccessResponse<String>> registerMainRecipe(
            @PathVariable("id") Long id,
            @PathVariable("recipeId") Long recipeId) {
        mainRecipeService.registerMainRecipe(id, recipeId);
        return toResponseEntity("메인 레시피 등록 성공", "레시피 ID: " + recipeId);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectMainRecipeResponseDto>>> getMainRecipeCandidates() {
        List<SelectMainRecipeResponseDto> response = mainRecipeService.getMainRecipeCandidates();
        return toResponseEntity("메인 레시피 후보 조회", response);
    }

    @DeleteMapping("{id}/recipes/{recipeId}")
    public ResponseEntity<SuccessResponse<String>> unregisterMainRecipe(
            @PathVariable("id") Long id,
            @PathVariable("recipeId") Long recipeId) {
        mainRecipeService.unregisterMainRecipe(id, recipeId);
        return toResponseEntity("메인 레시피 취소 성공", "레시피 ID: " + recipeId);
    }
}
