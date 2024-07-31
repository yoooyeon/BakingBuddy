package com.coco.bakingbuddy.ingredient.repository;

import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRecipeRepository extends JpaRepository<IngredientRecipe, Long> {

}
