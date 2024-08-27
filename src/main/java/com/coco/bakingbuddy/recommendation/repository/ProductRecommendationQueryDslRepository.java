package com.coco.bakingbuddy.recommendation.repository;

import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.recommendation.domain.QProductRecommendation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

//import static com.coco.bakingbuddy.recommendation.domain.QProductRecommendation.productRecommendation;

@RequiredArgsConstructor
@Repository
public class ProductRecommendationQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Product> findByRecipeId(Long id) {
        return queryFactory
                .select(QProductRecommendation.productRecommendation.product)
                .from(QProductRecommendation.productRecommendation)
                .where(QProductRecommendation.productRecommendation.recipe.id.eq(id))
                .fetch();
    }
}
