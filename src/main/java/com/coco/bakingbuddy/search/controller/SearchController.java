package com.coco.bakingbuddy.search.controller;

import com.coco.bakingbuddy.ranking.dto.response.SelectRankingTermsCacheResponseDto;
import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeSearchService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.repository.RedisAutoCompletePreviewDto;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.search.dto.RecentSearchResponseDto;
import com.coco.bakingbuddy.search.service.SearchService;
import com.coco.bakingbuddy.user.domain.PrincipalDetails;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

    @ResponseBody
    @GetMapping("recent")
    public List<RecentSearchResponseDto> recent(@AuthenticationPrincipal User user) {
        return userService.findRecentSearchesByUserId(user.getId());
    }

    @ResponseBody
    @GetMapping("popular")
    public List<SelectRankingTermsCacheResponseDto> popular() {
        return rankingService.selectTop10CachedRankingTerm();
    }


    @GetMapping("recipes")
    public String search(
            Model model,
            @RequestParam(name = "term", required = false) String term,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size
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
    @PostMapping("/term")
    public void saveSearchTerm(@RequestParam("term") String term) {
        redisService.saveSearchTerm(term);
    }

    @ResponseBody
    @GetMapping("/autocomplete")
    public List<RedisAutoCompletePreviewDto> autocomplete(@RequestParam("prefix") String prefix) {
        return redisService.autocomplete(prefix);
    }
}
