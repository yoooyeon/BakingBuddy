package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.QRecipeStep;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QDirectory.directory;
import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;
import static com.coco.bakingbuddy.recipe.domain.QRecipeStep.recipeStep;

@RequiredArgsConstructor
@Repository
public class RecipeStepQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public void delete(List<RecipeStep> recipeSteps) {
        queryFactory.delete(recipeStep)
                .where(recipeStep.in(recipeSteps))
                .execute();
    }
}
