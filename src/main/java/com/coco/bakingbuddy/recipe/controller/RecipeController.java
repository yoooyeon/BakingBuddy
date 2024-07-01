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
    private final DirectoryService directoryService;

    @GetMapping("users/{userId}")
    public String selectByUserId(@PathVariable Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/user";
    }

    @GetMapping("directories/{directoryId}")
    public List<SelectRecipeResponseDto> selectByDirectoryId(@PathVariable Long directoryId) {
        return recipeService.selectByDirectoryId(directoryId);
    }

    @GetMapping
    public String selectAll(Model model) {
        List<SelectRecipeResponseDto> dtos = recipeService.selectAll();
        model.addAttribute("recipes", dtos);
        model.addAttribute("isLoggedIn", true);
        return "recipe/recipe-list";
    }

    @GetMapping("{id}")
    public String selectById(@PathVariable Long id, Model model) {
        SelectRecipeResponseDto dto = recipeService.selectById(id);
        model.addAttribute("recipe", dto);
        return "recipe/recipe";
    }

    @GetMapping("register")
    public String register(Model model) {
        Long userId = 1L; // todo 임시 , 로그인 구현 후 userId 입력
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("dirs", dirs);
        return "recipe/register-recipe";
    }

    @PostMapping
    public CreateRecipeResponseDto create(@RequestBody CreateRecipeRequestDto dto) {
        return recipeService.create(dto);
    }

    @DeleteMapping
    public DeleteRecipeResponseDto create(@RequestBody DeleteRecipeRequestDto dto) {
        return recipeService.delete(dto);
    }

    @GetMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public String editRecipe(@PathVariable Long recipeId, Model model) {
        SelectRecipeResponseDto recipe = recipeService.selectById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/edit-recipe";
    }

    @PutMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public CreateRecipeResponseDto editRecipe(@PathVariable Long recipeId, @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return recipeService.edit(dto);
    }
}
