package com.coco.bakingbuddy.ingredient.repository;

import com.coco.bakingbuddy.ingredient.domain.Ingredient;
import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    // IngredientRepository
    public Map<Long, List<Ingredient>> findIngredientsByRecipeIds(List<Long> recipeIds) {

        List<IngredientRecipe> ingredientRecipes = queryFactory
                .selectFrom(ingredientRecipe)
                .leftJoin(ingredientRecipe.ingredient, ingredient).fetchJoin()
                .where(ingredientRecipe.recipe.id.in(recipeIds))
                .fetch();

        return ingredientRecipes.stream()
                .collect(Collectors.groupingBy(ir -> ir.getRecipe().getId(),
                        Collectors.mapping(IngredientRecipe::getIngredient, Collectors.toList())));
    }
}
