package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping("users/{userId}")
    public String selectByUserId(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user";
    }

    @GetMapping("directories/{directoryId}")
    public List<SelectRecipeResponseDto> selectByDirectoryId(@PathVariable Long directoryId) {
        return recipeService.selectByDirectoryId(directoryId);
    }

    @GetMapping
    public String selectAll(Model model) {
        List<SelectRecipeResponseDto> dtos = recipeService.selectAll();
        model.addAttribute("items", dtos);
        return "items";
    }

    @GetMapping("{id}")
    public String selectById(@PathVariable Long id, Model model) {
        SelectRecipeResponseDto dto = recipeService.selectById(id);
        model.addAttribute("item", dto);
        return "item";
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
