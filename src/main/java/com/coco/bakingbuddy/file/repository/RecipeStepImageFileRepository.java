package com.coco.bakingbuddy.file.repository;

import com.coco.bakingbuddy.file.domain.RecipeStepImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeStepImageFileRepository extends JpaRepository<RecipeStepImageFile, Long> {
}
