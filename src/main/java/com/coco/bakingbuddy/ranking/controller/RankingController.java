package com.coco.bakingbuddy.ranking.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
@Controller
public class RankingController {
    private final RankingService rankingService;

    @ResponseBody
    @GetMapping("/terms")
    public ResponseEntity<SuccessResponse<List<SelectRankingTermsCacheResponseDto>>> selectTop10CachedRankingTerm() {
        return toResponseEntity("인기 검색어 조회 성공", rankingService.selectTop10CachedRankingTerm());
    }

    @GetMapping("/clicks")
    public String clicks() {
        return "recipe/recipe-list";
    }

    @GetMapping("/recipes")
    public String recipes() {
        return "recipe/recipe-list";
    }
}
