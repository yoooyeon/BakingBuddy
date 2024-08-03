package com.coco.bakingbuddy.search.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeSearchService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.search.service.SearchService;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/search")
@RequiredArgsConstructor
@RestController
public class SearchController {

    private static final String DEFAULT_PAGE = "0";
    private static final String DEFAULT_SIZE = "10";
    private final RecipeService recipeService;
    private final RedisService redisService;
    private final RankingService rankingService;
    private final RecipeSearchService recipeSearchService;
    private final SearchService searchService;


    @GetMapping("recipes")
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> search(
            @RequestParam(name = "term", required = false) String term,
            @AuthenticationPrincipal User user) {

        processSearchTerm(term, user); // user history

        List<SelectRecipeResponseDto> selectRecipeResponseDtos;
        if (term != null && !term.isEmpty()) {
            selectRecipeResponseDtos = recipeSearchService.selectByTerm(term);
        } else {
            selectRecipeResponseDtos = recipeService.selectAll(user);
        }
        return toResponseEntity("레시피 페이지로 조회 성공", selectRecipeResponseDtos);
    }

    /**
     * @param term
     * @param page
     * @param size
     * @param user
     * @return
     * @GetMapping("recipes") public ResponseEntity<SuccessResponse<Map<String, Object>>> search(
     * @RequestParam(name = "term", required = false) String term,
     * @RequestParam(name = "page", defaultValue = DEFAULT_PAGE) int page,
     * @RequestParam(name = "size", defaultValue = DEFAULT_SIZE) int size,
     * @AuthenticationPrincipal User user) {
     * <p>
     * Page<SelectRecipeResponseDto> recipePage = getRecipePage(term, page, size);
     * processSearchTerm(term, user);
     * <p>
     * Map<String, Object> responseData = createResponseData(recipePage);
     * return toResponseEntity("레시피 페이지로 조회 성공", responseData);
     * }
     **/

    private Page<SelectRecipeResponseDto> getRecipePage(String term, int page, int size) {
        if (term != null && !term.isEmpty()) {
            return recipeSearchService.selectByTerm(term, PageRequest.of(page, size));
        } else {
            return recipeService.selectAll(PageRequest.of(page, size));
        }
    }

    /**
     * 사용자 검색어 기록 및 검색 카운팅
     *
     * @param term
     * @param user
     */
    private void processSearchTerm(String term, User user) {
        if (term != null && !term.isEmpty()) {
            redisService.saveSearchTerm(term);
            rankingService.incrementSearchCount(term);
            if (user != null) {
                searchService.addRecentSearch(user.getId(), term);
            }
        }
    }

    private Map<String, Object> createResponseData(Page<SelectRecipeResponseDto> recipePage) {
        Map<String, Object> responseData = new HashMap<>();
        responseData.put("recipes", recipePage.getContent());
        responseData.put("currentPage", recipePage.getNumber());
        responseData.put("totalPages", recipePage.getTotalPages());
        return responseData;
    }
}
