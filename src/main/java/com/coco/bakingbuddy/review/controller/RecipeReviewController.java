package com.coco.bakingbuddy.review.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.review.dto.request.CreateRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.EditRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.DeleteRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.service.RecipeReviewService;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@Slf4j
@RequestMapping("/api/recipe-reviews")
@RequiredArgsConstructor
@RestController
public class RecipeReviewController {
    private final RecipeReviewService recipeReviewService;

    /**
     * 모든 레시피 리뷰 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectRecipeReviewResponseDto>>> selectAll(
            @AuthenticationPrincipal User user) {
        return toResponseEntity("모든 레시피 리뷰 조회 성공",
                recipeReviewService.selectAll());
    }

    /**
     * 레시피 리뷰 아이디로 조회
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<SelectRecipeReviewResponseDto>> selectById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        return toResponseEntity("레시피 리뷰 아이디로 조회 성공",
                recipeReviewService.selectById(id, user));
    }
    /**
     * 레시피의 모든 리뷰 조회
     *
     * @param recipeId
     * @return
     */
    @GetMapping("recipes/{recipeId}")
    public ResponseEntity<SuccessResponse<List<SelectRecipeReviewResponseDto>>> selectByRecipeId(
            @PathVariable("recipeId") Long recipeId,
            @AuthenticationPrincipal User user) {
        return toResponseEntity("레시피 리뷰 아이디로 조회 성공",
                recipeReviewService.selectByRecipeId(recipeId, user));
    }
    /**
     * 레시피 리뷰 생성
     *
     * @param user
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateRecipeReviewResponseDto>> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateRecipeReviewRequestDto dto) {
        try {
            CreateRecipeReviewResponseDto savedReview = recipeReviewService.create(user.getId(), dto);
            return toResponseEntity("레시피 리뷰 생성 성공", savedReview);
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 레시피 리뷰 수정
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("{id}/edit")
    public ResponseEntity<SuccessResponse<CreateRecipeReviewResponseDto>> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody EditRecipeReviewRequestDto dto,
            @AuthenticationPrincipal User user) {
        dto.setId(id);
        return toResponseEntity("레시피 리뷰 수정 성공", recipeReviewService.edit(dto, user));
    }

    /**
     * 레시피 리뷰 삭제
     *
     * @param reviewId
     * @param user
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<DeleteRecipeReviewResponseDto>> delete(
            @PathVariable("id") Long reviewId,
            @AuthenticationPrincipal User user) {
        recipeReviewService.delete(reviewId, user);
        return toResponseEntity("레시피 리뷰 삭제 성공");
    }
}
