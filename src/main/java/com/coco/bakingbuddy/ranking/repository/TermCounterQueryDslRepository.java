package com.coco.bakingbuddy.ranking.repository;

import com.coco.bakingbuddy.ranking.domain.TermCounter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.ranking.domain.QTermCounter.termCounter;

@RequiredArgsConstructor
@Repository
public class TermCounterQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<TermCounter> selectTop10RankingTerms() {
        return queryFactory
                .selectFrom(termCounter)
                .orderBy(termCounter.count.desc())
                .limit(10)
                .fetch();
    }
}
