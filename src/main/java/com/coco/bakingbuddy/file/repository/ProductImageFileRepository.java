package com.coco.bakingbuddy.file.repository;

import com.coco.bakingbuddy.file.domain.ProductImageFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductImageFileRepository extends JpaRepository<ProductImageFile, Long> {
}
