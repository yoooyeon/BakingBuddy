package com.coco.bakingbuddy.ranking.service;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.repository.RankingTermCacheQueryDslRepository;
import com.coco.bakingbuddy.ranking.repository.RankingTermCacheRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingTermCacheRepository rankingRepository;
    private final RankingTermCacheQueryDslRepository rankingTermQueryDslRepository;

    public List<SelectRankingTermsCacheResponseDto> selectTop10RankingTerms() {
        List<RankingTermCache> rankingTerms = rankingTermQueryDslRepository.selectTop10RankingTerms();
        return rankingTerms.stream()
                .map(SelectRankingTermsCacheResponseDto::fromEntity)
                .collect(Collectors.toList());
    }
}
