package com.coco.bakingbuddy.batch.scheduler;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import com.coco.bakingbuddy.ranking.domain.TermCounter;
import com.coco.bakingbuddy.ranking.repository.RankingTermCacheRepository;
import com.coco.bakingbuddy.ranking.repository.TermCounterQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class RankingTermBatchUpdater {
    private final TermCounterQueryDslRepository termCounterQueryDslRepository;
    private final RankingTermCacheRepository rankingTermCacheRepository;

//    @Scheduled(cron = "0 0/5 * * * *") // 5분마다 실행

    /**
     * 매 시간 실행되는 배치
     * 검색어 카운트를 내림차순 정렬하여 상위 10개를 캐시 테이블에 저장
     */
//    @Scheduled(cron = "0 0 * * * *") // 매 시간마다 실행
    @Scheduled(cron = "0 */10 * * * *") // 10분마다 실행

    public void updateRankingTermCache() {
        List<TermCounter> topTerms = termCounterQueryDslRepository.selectTop10RankingTerms();
        List<RankingTermCache> cacheTerms = topTerms.stream()
                .map(term -> RankingTermCache.builder()
                        .term(term.getTerm())
                        .count(term.getCount())
                        .build())
                .collect(Collectors.toList());
        rankingTermCacheRepository.deleteAll();
        rankingTermCacheRepository.saveAll(cacheTerms);
        log.info(">>> Ranking terms cache updated.");
    }
}
