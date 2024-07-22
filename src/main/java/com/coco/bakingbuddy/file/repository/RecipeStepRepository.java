package com.coco.bakingbuddy.file.repository;

import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RecipeStepRepository extends JpaRepository<RecipeStep, Long> {
    Optional<List<RecipeStep>> findByRecipeId(Long id);
}
