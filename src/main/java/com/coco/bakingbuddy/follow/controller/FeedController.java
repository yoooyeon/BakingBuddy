package com.coco.bakingbuddy.follow.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequestMapping("/api/feed")
@RequiredArgsConstructor
@RestController
public class FeedController {
    private final RecipeService recipeService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>>
    getFeed(@AuthenticationPrincipal User user, @RequestParam(name = "cursor", required = false) Long cursor) {
        return toResponseEntity("피드 조회 성공",
                recipeService.selectFeed(user,cursor));
    }

}
