package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.IngredientRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRecipeRepository extends JpaRepository<IngredientRecipe, Long> {

}
