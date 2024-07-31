package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@RestController
public class RecipeManagementController {
    private final RecipeService recipeService;


    //    @GetMapping
//    public ResponseEntity<SuccessResponse<Page<SelectRecipeResponseDto>>> selectAll(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size) {
//        Page<SelectRecipeResponseDto> recipePage = null;
//        return toResponseEntity("레시피 조회 성공",
//                recipeService.selectAll(PageRequest.of(page, size)));
//    }
//    @GetMapping
//    public ResponseEntity<SuccessResponse<PageResponseDto<SelectRecipeResponseDto>>> selectAll(
//            @RequestParam(name = "page", defaultValue = "0") int page,
//            @RequestParam(name = "size", defaultValue = "10") int size) {
//        Page<SelectRecipeResponseDto> recipePage = recipeService.selectAll(PageRequest.of(page, size));
//        List<SelectRecipeResponseDto> recipes = recipePage.getContent();
//        int totalPages = recipePage.getTotalPages();
//        long totalElements = recipePage.getTotalElements();
//        PageResponseDto<SelectRecipeResponseDto> response = new PageResponseDto<>(recipes, totalPages, totalElements);
//        return toResponseEntity("레시피 조회 성공", response);
//    }

    /**
     * 모든 레시피 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> selectAll() {
        return toResponseEntity("레시피 조회 성공",
                recipeService.selectAll());
    }

    /**
     * 레시피 아이디로 조회
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<SelectRecipeResponseDto>> selectById(@PathVariable("id") Long id) {
        return toResponseEntity("레시피 아이디로 조회 성공", recipeService.selectById(id));
    }

    /**
     * 하나의 디렉토리에 해당하는 레시피 모두 조회
     *
     * @param directoryId
     * @return
     */
    @GetMapping("directories/{directoryId}")
    public ResponseEntity<SuccessResponse<List<SelectRecipeResponseDto>>> selectByDirId(
            @PathVariable("directoryId") Long directoryId) {
        return toResponseEntity("디렉토리에 해당하는 레시피 조회 성공",
                recipeService.selectByDirectoryId(directoryId));
    }

    /**
     * 레시피 생성
     *
     * @param user
     * @param dto
     * @param recipeImage
     * @return
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateRecipeResponseDto>> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestPart("recipe") CreateRecipeRequestDto dto,
            @RequestPart("recipeImage") MultipartFile recipeImage) {
        try {
            CreateRecipeResponseDto savedRecipe = recipeService.create(user.getId(), dto, recipeImage);
            return toResponseEntity("레시피 생성 성공", savedRecipe);
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 레시피 수정
     *
     * @param recipeId
     * @param dto
     * @return
     */
    @PutMapping("{recipeId}/edit")
    public ResponseEntity<SuccessResponse<CreateRecipeResponseDto>> edit(
            @PathVariable("recipeId") Long recipeId
            , @Valid @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return toResponseEntity("레시피 수정 성공", recipeService.edit(dto));
    }

    /**
     * 레시피 삭제
     *
     * @param recipeId
     * @param user
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<DeleteRecipeResponseDto>> delete(
            @PathVariable("id") Long recipeId,
            @AuthenticationPrincipal User user) {
        return toResponseEntity("레시피 삭제 성공", recipeService.delete(recipeId, user));

    }
}
