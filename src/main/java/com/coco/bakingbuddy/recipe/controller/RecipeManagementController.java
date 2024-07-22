package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeManagementController {
    private final RecipeService recipeService;
    private final DirectoryService directoryService;

    @GetMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public String editRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
        SelectRecipeResponseDto recipe = recipeService.selectById(recipeId);
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(recipe.getUserId());
        model.addAttribute("dirs", dirs);
        model.addAttribute("recipe", recipe);
        return "recipe/edit-recipe";
    }

    @GetMapping
    public String selectAll(
            Model model,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<SelectRecipeResponseDto> recipePage = null;
        recipePage = recipeService.selectAll(PageRequest.of(page, size));
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
    }

    @GetMapping("{id}")
    public String selectById(@PathVariable("id") Long id, Model model) {
        SelectRecipeResponseDto dto = recipeService.selectById(id);
        model.addAttribute("recipe", dto);
        return "recipe/recipe-detail";
    }

    @GetMapping("register")
    public String register(Model model, @RequestParam("userId") Long userId) {
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("dirs", dirs);
        return "recipe/register-recipe";
    }

    @ResponseBody
    @GetMapping("directories/{directoryId}")
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> selectByDirectoryId(
            @PathVariable("directoryId") Long directoryId) {
        return toResponseEntity("디렉토리에 해당하는 레시피 조회 성공",
                recipeService.selectByDirectoryId(directoryId));
    }

    @ResponseBody
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateRecipeResponseDto>> create(
            @Valid @RequestPart("dto") CreateRecipeRequestDto dto,
            @RequestPart("recipeImage") MultipartFile recipeImage) {
        try {
            CreateRecipeResponseDto savedRecipe = recipeService.create(dto, recipeImage);
            return toResponseEntity("레시피 생성 성공", savedRecipe);
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @ResponseBody
    @DeleteMapping
    public ResponseEntity<SuccessResponse<DeleteRecipeResponseDto>> create(
            @Valid @RequestBody DeleteRecipeRequestDto dto) {
        return toResponseEntity("레시피 삭제 성공", recipeService.delete(dto));

    }


    @ResponseBody
    @PutMapping("{recipeId}/edit")
    public ResponseEntity<SuccessResponse<CreateRecipeResponseDto>> editRecipe(
            @PathVariable("recipeId") Long recipeId
            , @Valid @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return toResponseEntity("레시피 수정 성공", recipeService.edit(dto));
    }
}
