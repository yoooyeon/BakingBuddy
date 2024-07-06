package com.coco.bakingbuddy.file.repository;

import com.coco.bakingbuddy.file.domain.ImageFile;
import com.coco.bakingbuddy.file.domain.RecipeImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeImageFileRepository extends JpaRepository<RecipeImageFile, Long> {
}
