package com.coco.bakingbuddy.recommendation.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.product.dto.response.SelectProductResponseDto;
import com.coco.bakingbuddy.recommendation.service.RecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
@RequestMapping("/api/recommendations")
@RequiredArgsConstructor
@RestController
public class RecommendationController {
    private final RecommendationService recommendationService;
    @GetMapping("recipes/{id}")
    public ResponseEntity<SuccessResponse<List<SelectProductResponseDto>>> selectByRecipeId(
            @PathVariable("id") Long recipeId) {
        return toResponseEntity("레시피 연관 상품 조회 성공",recommendationService.selectByRecipeId(recipeId));
    }
}
