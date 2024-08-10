package com.coco.bakingbuddy.review.repository;

import com.coco.bakingbuddy.review.domain.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeReviewRepository extends JpaRepository<RecipeReview, Long> {
}
