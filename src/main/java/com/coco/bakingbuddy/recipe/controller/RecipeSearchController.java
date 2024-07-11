package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeSearchService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeSearchController {
    private final RecipeService recipeService;
    private final RedisService redisService;
    private final RankingService rankingService;
    private final RecipeSearchService recipeSearchService;

    @GetMapping("search")
    public String search(@AuthenticationPrincipal User user,
                         Model model,
                         @RequestParam(name = "term", required = false) String term,
                         @RequestParam(name = "level", required = false) String level,
                         @RequestParam(name = "time", required = false) Integer time,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "6") int size) {
        // 키워드, 필터 조건을 기반으로 검색한 결과를 페이징하여 가져옴
//        Page<Recipe> recipePage = recipeService.findRecipes(keyword, difficulty, time, PageRequest.of(page, size));
//        log.info("user=" + user.toString());
        if (user != null) {
            model.addAttribute("user", user);
        }
        Page<SelectRecipeResponseDto> recipePage;
        if (term != null && !term.isEmpty()) {
            recipePage = recipeSearchService.selectByTerm(term, PageRequest.of(page, size));
            redisService.saveSearchTerm(term);
            rankingService.incrementSearchCount(term); // ranking counter
        } else {
            recipePage = recipeService.selectAll(PageRequest.of(page, size));
        }
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
    }

}
