package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;

@RequiredArgsConstructor
@Repository
public class RecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;


    public Recipe findById(Long id) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.id.eq(id))
                .fetchOne();
    }

    public List<Recipe> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.user.id.eq(userId))
                .fetch();
    }

    public List<Recipe> findByDirectoryId(Long directoryId) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.directory.id.eq(directoryId))
                .fetch();
    }
}
