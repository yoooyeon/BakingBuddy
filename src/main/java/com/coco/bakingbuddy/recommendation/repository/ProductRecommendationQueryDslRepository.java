package com.coco.bakingbuddy.recommendation.repository;

import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.domain.QProduct;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recommendation.domain.QProductRecommendation;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.product.domain.QProduct.product;
import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;
import static com.coco.bakingbuddy.recommendation.domain.QProductRecommendation.productRecommendation;

@RequiredArgsConstructor
@Repository
public class ProductRecommendationQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Product> findByRecipeId(Long id) {
        return queryFactory
                .select(productRecommendation.product)
                .from(productRecommendation)
                .where(productRecommendation.recipe.id.eq(id))
                .fetch();
    }
}
