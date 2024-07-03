package com.coco.bakingbuddy.tag.repository;

import com.coco.bakingbuddy.tag.domain.TagRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRecipeRepository extends JpaRepository<TagRecipe, Long> {
}
