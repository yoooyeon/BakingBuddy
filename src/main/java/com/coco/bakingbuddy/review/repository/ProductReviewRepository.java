package com.coco.bakingbuddy.review.repository;

import com.coco.bakingbuddy.review.domain.ProductReview;
import com.coco.bakingbuddy.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
    List<ProductReview> findByUser(User user);
}
