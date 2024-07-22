package com.coco.bakingbuddy.search.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeSearchService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.repository.RedisAutoCompletePreviewDto;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.search.dto.response.RecentSearchResponseDto;
import com.coco.bakingbuddy.search.service.SearchService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/search")
@RequiredArgsConstructor
@Controller
public class SearchController {
    private final RecipeService recipeService;
    private final RedisService redisService;
    private final RankingService rankingService;
    private final RecipeSearchService recipeSearchService;
    private final SearchService searchService;
    private final UserService userService;

    @GetMapping
    public String search() {
        return "search/search";
    }

    @GetMapping("recipes")
    public String search(
            Model model,
            @RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
            , @AuthenticationPrincipal User user) {
        Page<SelectRecipeResponseDto> recipePage;
        if (term != null && !term.isEmpty()) {
            recipePage = recipeSearchService.selectByTerm(term, PageRequest.of(page, size));
            redisService.saveSearchTerm(term);
            rankingService.incrementSearchCount(term); // ranking counter
            if (user != null) {
                searchService.addRecentSearch(user.getId(), term);
            }
        } else {
            recipePage = recipeService.selectAll(PageRequest.of(page, size));
        }
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
    }

    @ResponseBody
    @GetMapping("recent")
    public ResponseEntity<SuccessResponse<List<RecentSearchResponseDto>>> recent(@AuthenticationPrincipal User user) {
        return toResponseEntity("최근 검색어 조회 성공"
                , userService.findRecentSearchesByUserId(user.getId()));
    }

    @ResponseBody
    @GetMapping("popular")
    public ResponseEntity<SuccessResponse<List<SelectRankingTermsCacheResponseDto>>> popular() {
        return toResponseEntity("인기 검색어 조회 성공"
                , rankingService.selectTop10CachedRankingTerm());
    }

    @ResponseBody
    @PostMapping("/term")
    public ResponseEntity<SuccessResponse<String>> saveSearchTerm(@RequestParam("term") String term) {
        redisService.saveSearchTerm(term);
        return toResponseEntity("검색어 저장 성공");
    }

    @ResponseBody
    @GetMapping("/autocomplete")
    public ResponseEntity<SuccessResponse<List<RedisAutoCompletePreviewDto>>> autocomplete(@RequestParam("prefix") String prefix) {
        return toResponseEntity("검색어 자동완성 조회 성공"
                ,redisService.autocomplete(prefix));
    }
}
