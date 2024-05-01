package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
}
