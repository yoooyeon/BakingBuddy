package com.coco.bakingbuddy.recommendation.repository;

import com.coco.bakingbuddy.recommendation.domain.ProductRecommendation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRecommendationRepository extends JpaRepository<ProductRecommendation, Long> {
}
