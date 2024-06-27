package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.QIngredient;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.tag.domain.QTagRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QIngredient.ingredient;
import static com.coco.bakingbuddy.recipe.domain.QIngredientRecipe.ingredientRecipe;
import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;
import static com.coco.bakingbuddy.tag.domain.QTag.tag;
import static com.coco.bakingbuddy.tag.domain.QTagRecipe.tagRecipe;

@RequiredArgsConstructor
@Repository
public class RecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;


    public Recipe findById(Long id) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.id.eq(id))
                .fetchOne();
    }

    public List<Recipe> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.user.id.eq(userId))
                .fetch();
    }

    public List<Recipe> findByDirectoryId(Long directoryId) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.directory.id.eq(directoryId))
                .fetch();
    }

    public List<Recipe> findAll() {
        return queryFactory
                .select(recipe)
                .from(recipe)
                .leftJoin(recipe.ingredientRecipes, ingredientRecipe)
                .leftJoin(recipe.tagRecipes, tagRecipe)
                .leftJoin(tagRecipe.tag, tag)
                .fetch();
    }

}
