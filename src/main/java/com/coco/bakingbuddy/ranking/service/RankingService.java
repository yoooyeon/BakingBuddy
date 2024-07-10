package com.coco.bakingbuddy.ranking.service;

import com.coco.bakingbuddy.ranking.domain.RankingTermCache;
import com.coco.bakingbuddy.ranking.domain.TermCounter;
import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.repository.RankingTermCacheQueryDslRepository;
import com.coco.bakingbuddy.ranking.repository.TermCounterRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RankingService {
    private final RankingTermCacheQueryDslRepository rankingTermQueryDslRepository;
    private final TermCounterRepository termCounterRepository;

    public List<SelectRankingTermsCacheResponseDto> selectTop10CachedRankingTerm() {
        List<RankingTermCache> rankingTerms = rankingTermQueryDslRepository.selectTop10RankingTerms();
        return rankingTerms.stream()
                .map(SelectRankingTermsCacheResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public void incrementSearchCount(String searchTerm) {
        Optional<TermCounter> termCounterOptional = termCounterRepository.findByTerm(searchTerm);
        if (termCounterOptional.isPresent()) {
            TermCounter termCounter = termCounterOptional.get();
            termCounter = termCounter.updateCount();
            termCounterRepository.save(termCounter);
        } else {
            termCounterRepository.save(TermCounter.builder()
                    .term(searchTerm)
                    .count(1)
                    .build());
        }
    }
}
