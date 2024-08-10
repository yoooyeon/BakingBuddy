package com.coco.bakingbuddy.review.repository;

import com.coco.bakingbuddy.product.domain.ProductReview;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview,Long> {
    List<ProductReview> findByRecipeId(Long recipeId);
}
