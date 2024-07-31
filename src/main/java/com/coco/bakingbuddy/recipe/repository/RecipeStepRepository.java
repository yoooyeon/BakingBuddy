package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    Optional<List<RecipeStep>> findByRecipeIdOrderByStepNumberAsc(Long id);

    Optional<List<RecipeStep>> findByRecipeOrderByStepNumberAsc(Recipe savedRecipe);

    Optional<List<RecipeStep>> findByRecipe(Recipe recipe);
}
