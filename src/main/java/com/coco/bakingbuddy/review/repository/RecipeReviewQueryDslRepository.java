package com.coco.bakingbuddy.review.repository;

import com.coco.bakingbuddy.review.domain.RecipeReview;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.review.domain.QRecipeReview.recipeReview;
import static com.coco.bakingbuddy.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class RecipeReviewQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<RecipeReview> findByRecipeId(Long recipeId) {
        return queryFactory
                .selectFrom(recipeReview)
                .join(recipeReview.user, user).fetchJoin()
                .where(recipeReview.recipe.id.eq(recipeId))
                .fetch();
    }
}
