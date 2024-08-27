package com.coco.bakingbuddy.recipe.repository;

import com.coco.bakingbuddy.recipe.domain.QRecipe;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.redis.repository.RedisAutoCompletePreviewDto;
import com.coco.bakingbuddy.user.domain.User;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.ingredient.domain.QIngredientRecipe.ingredientRecipe;
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
                .selectFrom(QRecipe.recipe)
                .where(QRecipe.recipe.id.eq(id))
                .fetchOne();
    }

    public List<Recipe> findByUserId(Long userId) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .where(QRecipe.recipe.user.id.eq(userId))
                .fetch();
    }

    public List<Recipe> findByDirectoryId(Long directoryId) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .where(QRecipe.recipe.directory.id.eq(directoryId))
                .fetch();
    }


    public Page<Recipe> findAll(Pageable pageable) {
        QueryResults<Recipe> queryResults = queryFactory
                .selectFrom(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(QRecipe.recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    public Page<Recipe> findByKeyword(String keyword, Pageable pageable) {
        QueryResults<Recipe> queryResults = queryFactory
                .selectFrom(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(QRecipe.recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(
                        recipe.name.containsIgnoreCase(keyword)
                                .or(ingredientRecipe.id.isNull().and(stringTemplate("''").like(keyword))) // ingredientRecipe가 없거나 이름이 일치하는 경우
                                .or(tag.id.isNull().and(stringTemplate("''").like(keyword))) // tag가 없거나 이름이 일치하는 경우
                )
                .offset(pageable.getOffset()) // 페이지 시작 위치 설정
                .limit(pageable.getPageSize()) // 페이지 크기 설정
                .fetchResults();
        return new PageImpl<>(queryResults.getResults(), pageable, queryResults.getTotal());
    }

    public List<Recipe> findByKeyword(String keyword) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(QRecipe.recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(
                        recipe.name.containsIgnoreCase(keyword) // 레시피 이름으로 검색
                                .or(ingredientRecipe.ingredient.name.containsIgnoreCase(keyword)) // 재료로 검색
                                .or(tag.name.containsIgnoreCase(keyword)) // 태그로 검색
                )
                .fetchResults()
                .getResults();
    }


    public List<RedisAutoCompletePreviewDto> findPreviewByTerm(String term) {
        return queryFactory
                .select(Projections.constructor(RedisAutoCompletePreviewDto.class,
                        QRecipe.recipe.name,
                        QRecipe.recipe.id,
                        QRecipe.recipe.recipeImageUrl
                ))
                .from(QRecipe.recipe)
                .where(QRecipe.recipe.name.containsIgnoreCase(term))
                .fetch();
    }

    public List<Recipe> findByUsers(List<User> allFollowedUsers) {
        return queryFactory
                .selectFrom(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(QRecipe.recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(QRecipe.recipe.user.in(allFollowedUsers))
                .orderBy(QRecipe.recipe.createdDate.desc())
                .fetchResults()
                .getResults();
    }

    public List<Recipe> findByUsersAfterCursor(List<User> users, Long cursor) {
        JPAQuery<Recipe> query = queryFactory
                .selectFrom(QRecipe.recipe)
                .leftJoin(QRecipe.recipe.ingredientRecipes, ingredientRecipe).fetchJoin()
                .leftJoin(QRecipe.recipe.tagRecipes, tagRecipe).fetchJoin()
                .leftJoin(tagRecipe.tag, tag).fetchJoin()
                .where(QRecipe.recipe.user.in(users));

        if (cursor != null) {
            // cursor 이후의 레시피를 가져오도록 조건 추가
            query.where(QRecipe.recipe.id.gt(cursor));
        }

        return query.orderBy(QRecipe.recipe.createdDate.desc()) // createdDate로 변경
                .limit(10) // 페이지당 가져올 레시피 수 제한
                .fetch();
    }

}
