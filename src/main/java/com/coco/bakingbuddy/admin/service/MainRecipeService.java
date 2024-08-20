package com.coco.bakingbuddy.admin.service;

import com.coco.bakingbuddy.admin.domain.MainRecipe;
import com.coco.bakingbuddy.admin.dto.response.SelectMainRecipeResponseDto;
import com.coco.bakingbuddy.admin.repository.MainRecipeQueryDslRepository;
import com.coco.bakingbuddy.admin.repository.MainRecipeRepository;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.MAIN_RECIPE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MainRecipeService {

    private final MainRecipeRepository mainRecipeRepository;
    private final MainRecipeQueryDslRepository mainRecipeQueryDslRepository;
    private final RecipeRepository recipeRepository;

    public List<SelectRecipeResponseDto> getCurrentMainRecipes() {
        return mainRecipeQueryDslRepository.selectCurrentMainRecipe()
                .stream()
                .map(SelectRecipeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerMainRecipe(Long mainRecipeId, Long recipeId) {
        MainRecipe mainRecipe = mainRecipeRepository.findById(mainRecipeId)
                .orElseThrow(() -> new CustomException(MAIN_RECIPE_NOT_FOUND));
        mainRecipe.setIsCurrent(true);
        mainRecipeRepository.save(mainRecipe);
    }

    public List<SelectMainRecipeResponseDto> getMainRecipeCandidates() {
        List<MainRecipe> mainRecipeCandidates = mainRecipeQueryDslRepository.findAvailableMainRecipeCandidates();
        return mainRecipeCandidates.stream()
                .map(SelectMainRecipeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void unregisterMainRecipe(Long mainRecipeId, Long recipeId) {
        MainRecipe mainRecipe = mainRecipeRepository.findById(mainRecipeId)
                .orElseThrow(() -> new CustomException(MAIN_RECIPE_NOT_FOUND));
        mainRecipe.setIsCurrent(false);
        mainRecipeRepository.save(mainRecipe);
    }
}
