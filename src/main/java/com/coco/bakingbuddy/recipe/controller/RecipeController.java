package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@RestController
public class RecipeController {
    private final RecipeService recipeService;

    @GetMapping("users/{userId}")
    public List<SelectRecipeResponseDto> selectByUserId(@PathVariable Long userId) {
        return recipeService.selectByUserId(userId);
    }

    @GetMapping("directories/{directoryId}")
    public List<SelectRecipeResponseDto> selectByDirectoryId(@PathVariable Long directoryId) {
        return recipeService.selectByDirectoryId(directoryId);
    }

    @GetMapping
    public List<SelectRecipeResponseDto> selectAll() {
        return recipeService.selectAll();
    }

    @GetMapping("{id}")
    public SelectRecipeResponseDto selectById(@PathVariable Long id) {
        return recipeService.selectById(id);
    }

    @PostMapping
    public CreateRecipeResponseDto create(@RequestBody CreateRecipeRequestDto dto) {
        return recipeService.create(dto);
    }

    @DeleteMapping
    public DeleteRecipeResponseDto create(@RequestBody DeleteRecipeRequestDto dto) {
        return recipeService.delete(dto);
    }
}
