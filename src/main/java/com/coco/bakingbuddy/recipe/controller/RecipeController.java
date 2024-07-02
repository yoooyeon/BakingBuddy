package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final DirectoryService directoryService;

    @GetMapping
    public String selectAll(Model model) {
        List<SelectRecipeResponseDto> dtos = recipeService.selectAll();
        model.addAttribute("recipes", dtos);
        return "recipe/recipe-list";
    }

    @GetMapping("users/{userId}")
    public String selectByUserId(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/user";
    }

    @ResponseBody
    @GetMapping("directories/{directoryId}")
    public List<SelectRecipeResponseDto> selectByDirectoryId(@PathVariable("directoryId") Long directoryId) {
        return recipeService.selectByDirectoryId(directoryId);
    }


    @GetMapping("{id}")
    public String selectById(@PathVariable("id") Long id, Model model) {
        SelectRecipeResponseDto dto = recipeService.selectById(id);
        model.addAttribute("recipe", dto);
        return "recipe/recipe";
    }

    @GetMapping("register")
    public String register(Model model, @RequestParam("userId") Long userId) {
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("dirs", dirs);
        return "recipe/register-recipe";
    }

    @ResponseBody
    @PostMapping
    public CreateRecipeResponseDto create(@Valid @RequestBody CreateRecipeRequestDto dto) {
        CreateRecipeResponseDto save = recipeService.create(dto);
        return save;
    }

    @ResponseBody
    @DeleteMapping
    public DeleteRecipeResponseDto create(@Valid @RequestBody DeleteRecipeRequestDto dto) {
        return recipeService.delete(dto);
    }

    @GetMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public String editRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
        SelectRecipeResponseDto recipe = recipeService.selectById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/edit-recipe";
    }

    @ResponseBody
    @PutMapping("{recipeId}/edit")
    public CreateRecipeResponseDto editRecipe(@PathVariable("recipeId") Long recipeId, @Valid @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return recipeService.edit(dto);
    }
}
