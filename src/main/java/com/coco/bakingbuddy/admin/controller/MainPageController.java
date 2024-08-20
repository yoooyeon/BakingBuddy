package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.service.MainRecipeService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/main")
public class MainPageController {

    private final MainRecipeService mainRecipeService;

    @GetMapping("recipes")
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> mainRecipes() {
        return toResponseEntity("메인 레시피 조회 성공", mainRecipeService.getCurrentMainRecipes());
    }

}
