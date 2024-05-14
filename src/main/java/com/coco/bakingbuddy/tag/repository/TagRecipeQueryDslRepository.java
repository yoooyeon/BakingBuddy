package com.coco.bakingbuddy.tag.repository;

import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.tag.domain.QTagRecipe.tagRecipe;

@RequiredArgsConstructor
@Repository
public class TagRecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public TagRecipe findById(Long id) {
        return queryFactory
                .selectFrom(tagRecipe)
                .where(tagRecipe.id.eq(id))
                .fetchOne();
    }

    public List<TagRecipe> findByRecipeId(Long recipeId) {
        return queryFactory
                .selectFrom(tagRecipe)
                .where(tagRecipe.recipe.id.eq(recipeId))
                .fetch();
    }
}
