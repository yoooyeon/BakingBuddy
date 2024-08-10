package com.coco.bakingbuddy.review.domain;

import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.domain.ProductReview;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@MappedSuperclass
public abstract class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private int rating; // 1-5점 사이의 평점

    // 서브클래스에서 각 리뷰 타입의 관련 엔티티(Recipe, Product 등)를 설정해야 함
    public abstract void setRelatedEntity(Object entity);

    public static <T> Review create(User user, String content, int rating, T entity) {
        Review review = createReviewInstance(entity);
        review.user = user;
        review.content = content;
        review.rating = rating;
        review.setRelatedEntity(entity);
        return review;
    }

    // entity 타입에 따라 적절한 Review 인스턴스를 생성
    private static <T> Review createReviewInstance(T entity) {
        if (entity instanceof Product) {
            return new ProductReview();
        } else if (entity instanceof Recipe) {
            return new RecipeReview();
        }
        throw new IllegalArgumentException("Unsupported entity type");
    }
}
