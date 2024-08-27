package com.coco.bakingbuddy.tag.repository;

import com.coco.bakingbuddy.tag.domain.QTag;
import com.coco.bakingbuddy.tag.domain.QTagRecipe;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

//import static com.coco.bakingbuddy.tag.domain.QTag.tag;
//import static com.coco.bakingbuddy.tag.domain.QTagRecipe.tagRecipe;

@RequiredArgsConstructor
@Repository
public class TagRecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public TagRecipe findById(Long id) {
        return queryFactory
                .selectFrom(QTagRecipe.tagRecipe)
                .where(QTagRecipe.tagRecipe.id.eq(id))
                .fetchOne();
    }

    public List<TagRecipe> findByRecipeId(Long recipeId) {
        return queryFactory
                .selectFrom(QTagRecipe.tagRecipe)
                .where(QTagRecipe.tagRecipe.recipe.id.eq(recipeId))
                .fetch();
    }

    public List<Tag> findTagsByRecipeId(Long recipeId) {
        return queryFactory
                .select(QTag.tag)
                .from(QTagRecipe.tagRecipe)
                .join(QTagRecipe.tagRecipe.tag, QTag.tag)
                .where(QTagRecipe.tagRecipe.recipe.id.eq(recipeId))
                .fetch();
    }

    public Map<Long, List<Tag>> findTagsByRecipeIds(List<Long> recipeIds) {

        List<TagRecipe> tagRecipes = queryFactory
                .selectFrom(QTagRecipe.tagRecipe)
                .leftJoin(QTagRecipe.tagRecipe.tag, QTag.tag).fetchJoin()
                .where(QTagRecipe.tagRecipe.recipe.id.in(recipeIds))
                .fetch();

        return tagRecipes.stream()
                .collect(Collectors.groupingBy(tr -> tr.getRecipe().getId(),
                        Collectors.mapping(TagRecipe::getTag, Collectors.toList())));
    }
}
