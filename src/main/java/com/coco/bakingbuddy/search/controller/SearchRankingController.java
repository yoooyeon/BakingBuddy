package com.coco.bakingbuddy.search.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/search")
@RequiredArgsConstructor
@RestController
public class SearchRankingController {
    private final RankingService rankingService;

    @GetMapping("popular")
    public ResponseEntity<SuccessResponse<List<SelectRankingTermsCacheResponseDto>>>
    popular() {
        return toResponseEntity("인기 검색어 조회 성공"
                , rankingService.selectTop10CachedRankingTerm());
    }
}
