package com.coco.bakingbuddy.admin.repository;

import com.coco.bakingbuddy.admin.domain.MainRecipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Arrays;

@Repository
public interface MainRecipeRepository extends JpaRepository<MainRecipe, Long> {
    void updateAllToNotCurrent();

}
