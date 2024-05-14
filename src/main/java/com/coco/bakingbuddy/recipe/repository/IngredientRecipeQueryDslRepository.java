package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.IngredientRecipe;
import com.coco.bakingbuddy.recipe.domain.QIngredientRecipe;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QIngredientRecipe.*;
import static com.coco.bakingbuddy.tag.domain.QTagRecipe.tagRecipe;

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
}
