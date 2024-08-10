package com.coco.bakingbuddy.review.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.review.dto.request.CreateProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.DeleteProductReviewResponseDto;
import com.coco.bakingbuddy.review.dto.request.EditProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateProductReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectProductReviewResponseDto;
import com.coco.bakingbuddy.review.service.ProductReviewService;
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
@RequestMapping("/api/product-reviews")
@RequiredArgsConstructor
@RestController
public class ProductReviewController {
    private final ProductReviewService productReviewService;

    /**
     * 모든 상품 리뷰 조회
     *
     * @return
     */
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectProductReviewResponseDto>>> selectAll(
            @AuthenticationPrincipal User user) {
        return toResponseEntity("상품 리뷰 조회 성공",
                productReviewService.selectAll(user));
    }

    /**
     * 상품 리뷰 아이디로 조회
     *
     * @param id
     * @return
     */
    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<List<SelectProductReviewResponseDto>>> selectById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal User user) {
        return toResponseEntity("상품 리뷰 아이디로 조회 성공",
                productReviewService.selectById(id));
    }

    /**
     * 상품 리뷰 유저 아이디로 조회
     *
     * @return
     */
    @GetMapping("users")
    public ResponseEntity<SuccessResponse<List<SelectProductReviewResponseDto>>> selectByUserId(
            @AuthenticationPrincipal User user) {
        return toResponseEntity("상품 리뷰 유저 아이디로 조회 성공",
                productReviewService.selectById(user.getId()));
    }


    /**
     * 상품 리뷰 생성
     *
     * @param user
     * @param dto
     * @return
     */
    @PostMapping
    public ResponseEntity<SuccessResponse<CreateProductReviewResponseDto>> create(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreateProductReviewRequestDto dto) {
        try {
            CreateProductReviewResponseDto savedReview = productReviewService.create(user.getId(), dto);
            return toResponseEntity("상품 리뷰 생성 성공", savedReview);
        } catch (Exception e) {
            // 예외 처리 및 적절한 응답 반환
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(null);
        }
    }

    /**
     * 상품 리뷰 수정
     *
     * @param id
     * @param dto
     * @return
     */
    @PutMapping("{id}/edit")
    public ResponseEntity<SuccessResponse<CreateProductReviewResponseDto>> edit(
            @PathVariable("id") Long id,
            @Valid @RequestBody EditProductReviewRequestDto dto,
            @AuthenticationPrincipal User user) {
//        dto.setId(reviewId);
        return toResponseEntity("상품 리뷰 수정 성공", productReviewService.edit(dto, user));
    }

    /**
     * 상품 리뷰 삭제
     *
     * @param reviewId
     * @param user
     * @return
     */
    @DeleteMapping("{id}")
    public ResponseEntity<SuccessResponse<DeleteProductReviewResponseDto>> delete(
            @PathVariable("id") Long reviewId,
            @AuthenticationPrincipal User user) {
        return toResponseEntity("상품 리뷰 삭제 성공", productReviewService.delete(reviewId, user));
    }
}
