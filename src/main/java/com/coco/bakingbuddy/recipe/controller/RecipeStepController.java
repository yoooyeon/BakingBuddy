package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeStepResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes/{recipeId}/steps")
public class RecipeStepController {
    private final RecipeStepService recipeStepService;

    @PostMapping
    public ResponseEntity<SuccessResponse<CreateRecipeStepResponseDto>> addStep(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("stepNumber") Integer stepNumber,
            @RequestParam("description") String description,
            @RequestParam(value = "stepImage",required = false) MultipartFile stepImage) {
        return toResponseEntity("RecipeStep 저장 성공",
                recipeStepService.addStep(recipeId, stepNumber, description, stepImage));
    }
}
