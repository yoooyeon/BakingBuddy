package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.*;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@RestController
public class RecipeManagementController {
    private final RecipeService recipeService;
    private final DirectoryService directoryService;

    @GetMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public ResponseEntity<SuccessResponse<Map<String, Object>>> editRecipe(@PathVariable("recipeId") Long recipeId) {
        Map<String, Object> responseData = new HashMap<>();
        SelectRecipeResponseDto recipe = recipeService.selectById(recipeId);
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(recipe.getUserId());
        responseData.put("recipe", recipe);
        responseData.put("directories", dirs);
        return toResponseEntity("레시피 수정 완료", responseData);
    }

    //    @GetMapping
//    public ResponseEntity<SuccessResponse<Page<SelectRecipeResponseDto>>> selectAll(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size) {
//        Page<SelectRecipeResponseDto> recipePage = null;
//        return toResponseEntity("레시피 조회 성공",
//                recipeService.selectAll(PageRequest.of(page, size)));
//    }
    @GetMapping
    public ResponseEntity<SuccessResponse<PageResponseDto<SelectRecipeResponseDto>>> selectAll(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<SelectRecipeResponseDto> recipePage = recipeService.selectAll(PageRequest.of(page, size));
        List<SelectRecipeResponseDto> recipes = recipePage.getContent();
        int totalPages = recipePage.getTotalPages();
        long totalElements = recipePage.getTotalElements();
        PageResponseDto<SelectRecipeResponseDto> response = new PageResponseDto<>(recipes, totalPages, totalElements);
        return toResponseEntity("레시피 조회 성공", response);
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<SelectRecipeResponseDto>> selectById(@PathVariable("id") Long id) {
        return toResponseEntity("디렉토리 아이디로 조회 성공", recipeService.selectById(id));
    }

    @GetMapping("register")
    public ResponseEntity<SuccessResponse<List<SelectDirectoryResponseDto>>> register(@RequestParam("userId") Long userId) {
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(userId);
        return toResponseEntity("디렉토리 등록 성공", directoryService.selectByUserId(userId));
    }

    @GetMapping("directories/{directoryId}")
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> selectByDirectoryId(
            @PathVariable("directoryId") Long directoryId) {
        return toResponseEntity("디렉토리에 해당하는 레시피 조회 성공",
                recipeService.selectByDirectoryId(directoryId));
    }

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

    @DeleteMapping
    public ResponseEntity<SuccessResponse<DeleteRecipeResponseDto>> create(
            @Valid @RequestBody DeleteRecipeRequestDto dto) {
        return toResponseEntity("레시피 삭제 성공", recipeService.delete(dto));

    }

    @PutMapping("{recipeId}/edit")
    public ResponseEntity<SuccessResponse<CreateRecipeResponseDto>> editRecipe(
            @PathVariable("recipeId") Long recipeId
            , @Valid @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return toResponseEntity("레시피 수정 성공", recipeService.edit(dto));
    }
}
