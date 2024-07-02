package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QIngredientRecipe.ingredientRecipe;
import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;
import static com.coco.bakingbuddy.tag.domain.QTag.tag;
import static com.coco.bakingbuddy.tag.domain.QTagRecipe.tagRecipe;
import static com.querydsl.core.types.dsl.Expressions.stringTemplate;
@Slf4j
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

    public List<Recipe> findAll() {
        return queryFactory
                .select(recipe)
                .from(recipe)
                .leftJoin(recipe.ingredientRecipes, ingredientRecipe)
                .leftJoin(recipe.tagRecipes, tagRecipe)
                .leftJoin(tagRecipe.tag, tag)
                .fetch();
    }

    public Page<Recipe> findAll(Pageable pageable) {
        QueryResults<Recipe> queryResults = queryFactory
                .selectFrom(recipe)
                .leftJoin(recipe.ingredientRecipes, ingredientRecipe)
                .leftJoin(recipe.tagRecipes, tagRecipe)
                .leftJoin(tagRecipe.tag, tag)
                .offset(pageable.getOffset()) // 페이지 시작 위치 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    public Page<Recipe> findByKeyword(String keyword, Pageable pageable) {
        QueryResults<Recipe> queryResults = queryFactory
                .selectFrom(recipe)
                .leftJoin(recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(
                        recipe.name.containsIgnoreCase(keyword)
                                .or(ingredientRecipe.id.isNull().and(stringTemplate("''").like(keyword))) // ingredientRecipe가 없거나 이름이 일치하는 경우
                                .or(tag.id.isNull().and(stringTemplate("''").like(keyword))) // tag가 없거나 이름이 일치하는 경우
                )
                .offset(pageable.getOffset()) // 페이지 시작 위치 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetchResults();
        log.info("queryResults.getTotal(),", queryResults.getTotal());
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());

//        return recipes;
    }
}
