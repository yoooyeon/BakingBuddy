package com.coco.bakingbuddy.admin.repository;

import com.coco.bakingbuddy.admin.domain.MainRecipe;
//import com.coco.bakingbuddy.admin.domain.QMainRecipe;
import com.coco.bakingbuddy.admin.domain.QMainRecipe;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

//import static com.coco.bakingbuddy.admin.domain.QMainRecipe.mainRecipe;
//import static com.coco.bakingbuddy.recipe.domain.QRecipe.recipe;

@RequiredArgsConstructor
@Repository
public class MainRecipeQueryDslRepository {
    private final JPAQueryFactory queryFactory;
    public List<Recipe> selectCurrentMainRecipe() {
        return queryFactory
                .select(QMainRecipe.mainRecipe.recipe)
                .from(QMainRecipe.mainRecipe)
                .where(QMainRecipe.mainRecipe.isCurrent)
                .fetch();
    }

    public void updateAllToNotCurrent() {
        queryFactory
                .update(QMainRecipe.mainRecipe)
                .set(QMainRecipe.mainRecipe.isCurrent, false)
                .execute();
    }

    // todo 로직 고민중
    public List<MainRecipe> findAvailableMainRecipeCandidates() {
        return queryFactory
                .selectFrom(QMainRecipe.mainRecipe)
//                .where(mainRecipe.isCurrent.eq(false)) // 현재 메인 레시피가 아닌 레시피를 조회
                .fetch();
    }
}
