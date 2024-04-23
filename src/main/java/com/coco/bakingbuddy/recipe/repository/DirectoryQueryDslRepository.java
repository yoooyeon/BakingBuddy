package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QDirectory.directory;
import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;

@RequiredArgsConstructor
@Repository
public class DirectoryQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Recipe> findByRecipeId(Long id) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.id.eq(id))
                .fetch();
    }

    public List<Directory> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(directory)
                .where(directory.user.id.eq(userId))
                .fetch();
    }
}
