package com.coco.bakingbuddy.search.repository;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.search.domain.SearchRecord;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;
import static com.coco.bakingbuddy.search.domain.QSearchRecord.searchRecord;
import static com.coco.bakingbuddy.user.domain.QUser.user;

@RequiredArgsConstructor
@Repository
public class SearchRecordQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<Recipe> findByRecipeId(Long id) {
        return queryFactory
                .selectFrom(recipe)
                .where(recipe.id.eq(id))
                .fetch();
    }

    public SearchRecord selectSearchRecordsByUserIdAndTerm(Long userId, String term) {
        return queryFactory.selectFrom(searchRecord)
                .innerJoin(searchRecord.user, user)
                .where(user.id.eq(userId)
                        .and(searchRecord.term.eq(term)))
                .fetchOne();
    }
}
