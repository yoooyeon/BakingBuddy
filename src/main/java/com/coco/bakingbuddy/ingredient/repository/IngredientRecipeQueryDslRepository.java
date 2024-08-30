package com.coco.bakingbuddy.ingredient.repository;

import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.ingredient.domain.QIngredient;
import com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe;
import com.coco.bakingbuddy.ingredient.dto.response.IngredientResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.ingredient.domain.QIngredient.ingredient;
import static com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe.ingredientRecipe;

//import static com.coco.bakingbuddy.ingredient.domain.QIngredient.ingredient;
//import static com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe.ingredientRecipe;


@RequiredArgsConstructor
@Repository
public class IngredientRecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;


    public List<IngredientRecipe> findByRecipeId(Long recipeId) {
        return queryFactory
                .selectFrom(ingredientRecipe)
                .where(ingredientRecipe.recipe.id.eq(recipeId))
                .fetch();
    }

    public List<IngredientResponseDto> findIngredientsByRecipeId(Long recipeId) {
        return queryFactory
                .select(Projections.fields(IngredientResponseDto.class,
                        ingredientRecipe.recipe.id.as("recipeId"),
                        ingredient.name.as("name"),
                        ingredientRecipe.unit.as("unit"), // Unit의 displayName으로 설정
                        ingredientRecipe.amount.as("amount"),
                        ingredientRecipe.servings.as("servings")))
                .from(ingredientRecipe)
                .join(ingredientRecipe.ingredient, ingredient)
                .where(ingredientRecipe.recipe.id.eq(recipeId))
                .fetch();
    }

    // IngredientRepository
    public Map<Long, List<IngredientResponseDto>> findIngredientsByRecipeIds(List<Long> recipeIds) {

        List<IngredientResponseDto> ingredientRecipes = queryFactory
                .select(Projections.fields(IngredientResponseDto.class,
                        ingredientRecipe.recipe.id.as("recipeId"),
                        ingredient.name.as("name"),
                        ingredientRecipe.unit.as("unit"), // Unit의 displayName으로 설정
                        ingredientRecipe.amount.as("amount"),
                        ingredientRecipe.servings.as("servings")))
                .from(ingredientRecipe)
                .leftJoin(ingredientRecipe.ingredient, ingredient)
                .where(ingredientRecipe.recipe.id.in(recipeIds))
                .fetch();

        return ingredientRecipes.stream()
                .collect(Collectors.groupingBy(ir -> ir.getRecipeId()));
    }
}
