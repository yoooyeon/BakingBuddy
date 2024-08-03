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

    public CreateRecipeStepResponseDto
    addStep(Long recipeId, Integer stepNumber, String description, MultipartFile stepImage) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));

        String imageUrl = null;
        if (stepImage != null && !stepImage.isEmpty()) {
            imageUrl = fileService.uploadRecipeStepImage(stepImage);
        }
        RecipeStep recipeStep = recipeStepRepository.save(RecipeStep.builder()
                .stepNumber(stepNumber)
                .description(description)
                .recipe(recipe)
                .stepImage(imageUrl)
                .build());

        return CreateRecipeStepResponseDto.fromEntity(recipeStep);
    }

    public CreateRecipeStepResponseDto editStep(Long recipeId, Integer stepNumber, String description, MultipartFile stepImage) {

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        List<RecipeStep> recipeSteps = recipeStepRepository.findByRecipe(recipe).orElseThrow(() -> new CustomException(RECIPE_STEP_NOT_FOUND));
        recipeStepQueryDslRepository.delete(recipeSteps);
        String imageUrl = null;
        if (stepImage != null && !stepImage.isEmpty()) {
            imageUrl = fileService.uploadRecipeStepImage(stepImage);
        }
        RecipeStep recipeStep = recipeStepRepository.save(RecipeStep.builder()
                .stepNumber(stepNumber)
                .description(description)
                .recipe(recipe)
                .stepImage(imageUrl)
                .build());

        return CreateRecipeStepResponseDto.fromEntity(recipeStep);

    }
}
