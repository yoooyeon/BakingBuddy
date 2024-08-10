package com.coco.bakingbuddy.recommendation.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ProductRecommendation extends BaseTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RECOMMENDATION_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe; // Recipe와의 ManyToOne 관계

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product; // Product와의 ManyToOne 관계

    private String recommendationReason; // 추천 이유 등 추가 정보
}
