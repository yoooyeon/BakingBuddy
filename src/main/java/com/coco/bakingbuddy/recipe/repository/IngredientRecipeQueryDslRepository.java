package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.IngredientRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QIngredient.ingredient;
import static com.coco.bakingbuddy.recipe.domain.QIngredientRecipe.ingredientRecipe;

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

    public List<Ingredient> findIngredientsByRecipeId(Long recipeId) {
        return queryFactory
                .select(ingredient)
                .from(ingredientRecipe)
                .leftJoin(ingredientRecipe.ingredient, ingredient)
                .where(ingredientRecipe.recipe.id.eq(recipeId))
                .fetch();
    }
}