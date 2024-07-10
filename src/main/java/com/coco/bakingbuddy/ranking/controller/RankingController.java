package com.coco.bakingbuddy.ranking.controller;

import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.service.RankingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/ranking")
@Controller
public class RankingController {
    private final RankingService rankingService;
    @GetMapping("/terms")
    public List<SelectRankingTermsCacheResponseDto> selectTop10CachedRankingTerm() {
        return rankingService.selectTop10CachedRankingTerm();
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
