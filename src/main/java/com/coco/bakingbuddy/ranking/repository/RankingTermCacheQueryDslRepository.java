package com.coco.bakingbuddy.ranking.repository;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.coco.bakingbuddy.ranking.domain.QRankingTerm.rankingTerm;

@RequiredArgsConstructor
@Repository
public class RankingTermCacheQueryDslRepository {
    private final JPAQueryFactory queryFactory;

    public List<RankingTermCache> selectTop10RankingTerms() {
        return queryFactory
                .selectFrom(rankingTerm)
                .orderBy(rankingTerm.count.desc())
                .limit(10)
                .fetch();
    }
}
