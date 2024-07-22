package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeStepRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeStepResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeStepService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recipes/{recipeId}/steps")
public class RecipeStepController {


    private final RecipeStepService recipeStepService;

    @PostMapping
    public ResponseEntity<CreateRecipeStepResponseDto> addStep(
            @PathVariable("recipeId") Long recipeId,
            @RequestParam("stepNumber") Integer stepNumber,
            @RequestParam("description") String description,
            @RequestParam("stepImage") MultipartFile stepImage) {
        CreateRecipeStepResponseDto addedStep = recipeStepService.addStep(recipeId, stepNumber, description, stepImage);
        return ResponseEntity.ok(addedStep);
    }
}
