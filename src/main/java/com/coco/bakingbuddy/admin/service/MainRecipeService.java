package com.coco.bakingbuddy.admin.service;

import com.coco.bakingbuddy.admin.domain.MainRecipe;
import com.coco.bakingbuddy.admin.repository.MainRecipeQueryDslRepository;
import com.coco.bakingbuddy.admin.repository.MainRecipeRepository;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.RECIPE_NOT_FOUND;

@RequiredArgsConstructor
@Service
public class MainRecipeService {
    private final MainRecipeRepository mainRecipeRepository;
    private final MainRecipeQueryDslRepository mainRecipeQueryDslRepository;
    private final RecipeRepository recipeRepository;

    public List<SelectRecipeResponseDto> selectMainRecipes() {
        return mainRecipeQueryDslRepository.selectCurrentMainRecipe()
                .stream().map(SelectRecipeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void registerMainRecipe(Long recipeId) {
        // 현재 메인 레시피를 비활성화
        mainRecipeQueryDslRepository.updateAllToNotCurrent();

        // 새로운 레시피를 메인 레시피로 설정
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(RECIPE_NOT_FOUND));

        MainRecipe mainRecipe = MainRecipe.builder()
                .recipe(recipe)
                .isCurrent(true)
                .build();

        mainRecipeRepository.save(mainRecipe);
    }

    public List<SelectRecipeResponseDto> selectMainRecipeCandidates() {
        List<MainRecipe> mainRecipeCandidates = mainRecipeQueryDslRepository.findAvailableMainRecipeCandidates();

        List<Recipe> recipes = mainRecipeCandidates.stream()
                .map(MainRecipe::getRecipe)
                .collect(Collectors.toList());

        return recipes.stream()
                .map(SelectRecipeResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
