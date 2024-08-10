package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeStepResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeStepQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeStepRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.coco.bakingbuddy.global.error.ErrorCode.RECIPE_STEP_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class RecipeStepService {
    private final RecipeRepository recipeRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final FileService fileService;
    private final RecipeStepQueryDslRepository recipeStepQueryDslRepository;

    public CreateRecipeStepResponseDto addStep(Long recipeId, Integer stepNumber, String description, MultipartFile stepImage) {
        Recipe recipe = fetchRecipe(recipeId);
        return saveOrUpdateStep(recipe, stepNumber, description, stepImage);
    }

    public CreateRecipeStepResponseDto editStep(Long recipeId, Integer stepNumber, String description, MultipartFile stepImage) {
        Recipe recipe = fetchRecipe(recipeId);

        // 기존 조리 순서 제거
        List<RecipeStep> recipeSteps = recipeStepRepository.findByRecipe(recipe)
                .orElseThrow(() -> new CustomException(RECIPE_STEP_NOT_FOUND));
        recipeStepQueryDslRepository.delete(recipeSteps);
        return saveOrUpdateStep(recipe, stepNumber, description, stepImage);
    }

    private CreateRecipeStepResponseDto saveOrUpdateStep(Recipe recipe, Integer stepNumber, String description, MultipartFile stepImage) {
        RecipeStep recipeStep = RecipeStep.builder()
                .stepNumber(stepNumber)
                .description(description)
                .recipe(recipe)
                .build();

        recipeStep = recipeStepRepository.save(recipeStep);

        if (stepImage != null && !stepImage.isEmpty()) { // 이미지 있으면 파일서비스로 저장
            String imageUrl = fileService.uploadRecipeStepImage(recipeStep.getId(), stepImage);
            recipeStep.updateImage(imageUrl);
        }
        return CreateRecipeStepResponseDto.fromEntity(recipeStep);
    }

    private Recipe fetchRecipe(Long recipeId) {
        return recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
    }
}
