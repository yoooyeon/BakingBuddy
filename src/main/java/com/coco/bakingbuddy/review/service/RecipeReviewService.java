package com.coco.bakingbuddy.review.service;

import com.coco.bakingbuddy.review.dto.request.CreateRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.EditRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.DeleteRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.repository.RecipeReviewRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RecipeReviewService {
    private final RecipeReviewRepository recipeReviewRepository;

    public List<SelectRecipeReviewResponseDto> selectAll(User user) {
        // 리뷰 조회 로직
        return null;
    }

    public SelectRecipeReviewResponseDto selectById(Long reviewId, User user) {
        // 리뷰 상세 조회 로직
        return null;
    }

    public CreateRecipeReviewResponseDto create(Long userId, CreateRecipeReviewRequestDto dto) {
        // 리뷰 생성 로직
        return null;
    }

    public CreateRecipeReviewResponseDto edit(EditRecipeReviewRequestDto dto, User user) {
        // 리뷰 수정 로직
        return null;
    }

    public DeleteRecipeReviewResponseDto delete(Long reviewId, User user) {
        // 리뷰 삭제 로직
        return null;
    }
}

