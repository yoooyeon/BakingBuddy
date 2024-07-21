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
import com.coco.bakingbuddy.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeManagementController {
    private final RecipeService recipeService;
    private final DirectoryService directoryService;
    private final UserRepository userRepository;

    @GetMapping
    public String selectAll(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "6") int size) {
        Page<SelectRecipeResponseDto> recipePage = null;
        recipePage = recipeService.selectAll(PageRequest.of(page, size));
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
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
    public CreateRecipeResponseDto create(@Valid @RequestPart("dto") CreateRecipeRequestDto dto,
                                          @RequestParam("recipeImage") MultipartFile recipeImage
//            , @RequestParam("stepImages") MultipartFile[] stepImages
    ) {
        for (String ingredient : dto.getIngredients()) {
            log.info(">>>CreateRecipeResponseDto{}",ingredient);
        }
        CreateRecipeResponseDto save = recipeService.create(dto, recipeImage);
//        CreateRecipeResponseDto save = recipeService.create(dto, recipeImage, stepImages);
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
