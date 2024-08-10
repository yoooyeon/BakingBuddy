package com.coco.bakingbuddy.review.service;

import com.coco.bakingbuddy.review.dto.request.CreateProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.DeleteProductReviewResponseDto;
import com.coco.bakingbuddy.review.dto.request.EditProductReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateProductReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectProductReviewResponseDto;
import com.coco.bakingbuddy.review.repository.ProductReviewRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewService {
    private final ProductReviewRepository productReviewRepository;

    public List<SelectProductReviewResponseDto> selectAll(User user) {
        // 리뷰 조회 로직
        return null;
    }
    public List<SelectProductReviewResponseDto> selectById(Long reviewId) {
        // 리뷰 상세 조회 로직
        return null;
    }
    public List<SelectProductReviewResponseDto> selectByUserId(User user) {
        // 리뷰 상세 조회 로직
        return null;
    }

    public List<SelectProductReviewResponseDto> selectByRecipeId( Long recipeId) {
        // 리뷰 상세 조회 로직
        return null;
    }

    public CreateProductReviewResponseDto create(Long userId, CreateProductReviewRequestDto dto) {
        // 리뷰 생성 로직
        return null;
    }

    public CreateProductReviewResponseDto edit(EditProductReviewRequestDto dto, User user) {
        // 리뷰 수정 로직
        return null;
    }

    public DeleteProductReviewResponseDto delete(Long reviewId, User user) {
        // 리뷰 삭제 로직
        return null;
    }
}
