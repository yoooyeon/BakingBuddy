package com.coco.bakingbuddy.product.domain;

import com.coco.bakingbuddy.review.domain.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class ProductReview extends Review {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCT_ID", nullable = false)
    private Product product;

    @Override
    public void setRelatedEntity(Object entity) {
        if (entity instanceof Product) {
            this.product = (Product) entity;
        } else {
            throw new IllegalArgumentException("Invalid entity type for ProductReview");
        }
    }
}
