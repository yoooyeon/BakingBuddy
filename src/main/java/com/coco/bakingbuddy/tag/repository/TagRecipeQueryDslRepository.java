package com.coco.bakingbuddy.tag.repository;

import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.tag.domain.QTag.tag;
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

    public List<Tag> findTagsByRecipeId(Long recipeId) {
        return queryFactory
                .select(tag)
                .from(tagRecipe)
                .join(tagRecipe.tag, tag)
                .where(tagRecipe.recipe.id.eq(recipeId))
                .fetch();
    }

    public Map<Long, List<Tag>> findTagsByRecipeIds(List<Long> recipeIds) {

        List<TagRecipe> tagRecipes = queryFactory
                .selectFrom(tagRecipe)
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(tagRecipe.recipe.id.in(recipeIds))
                .fetch();

        return tagRecipes.stream()
                .collect(Collectors.groupingBy(tr -> tr.getRecipe().getId(),
                        Collectors.mapping(TagRecipe::getTag, Collectors.toList())));
    }
}
