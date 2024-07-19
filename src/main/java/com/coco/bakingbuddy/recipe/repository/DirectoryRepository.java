package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Directory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DirectoryRepository extends JpaRepository<Directory, Long> {
    boolean existsByName(String name);
}
