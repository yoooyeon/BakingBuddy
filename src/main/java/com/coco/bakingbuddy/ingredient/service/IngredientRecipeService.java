package com.coco.bakingbuddy.ingredient.service;

import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.ingredient.dto.IngredientResponseDto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.math.RoundingMode.HALF_UP;

@Service
public class IngredientRecipeService {

    // (기준 인분) -> (새 인분) 으로 계산해서 반환
    public List<IngredientResponseDto> getIngredientByServings(Recipe recipe, int newServings) {
        List<IngredientResponseDto> results = new ArrayList<>();
        for (IngredientRecipe ingredient : recipe.getIngredientRecipes()) {
            int originalServings = ingredient.getServings();
            BigDecimal originalAmount = ingredient.getAmount();

            // 새로운 양을 계산, 반올림
            BigDecimal newAmount = originalAmount.multiply(BigDecimal.valueOf(newServings))
                    .divide(BigDecimal.valueOf(originalServings), HALF_UP);

            results.add(IngredientResponseDto.builder()
                    .amount(newAmount)
                    .servings(newServings)
                    .name(ingredient.getIngredient().getName())
                    .build());
        }
        return results;
    }
}
