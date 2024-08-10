package com.coco.bakingbuddy.review.dto.response;

import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.dto.response.CreateProductResponseDto;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectRecipeReviewResponseDto {
    private Long id;
    private SelectUserResponseDto user;
    private String content;
    private int rating; // 1-5점 사이의 평점
    public static SelectRecipeReviewResponseDto fromEntity(RecipeReview recipeReview) {
        return SelectRecipeReviewResponseDto.builder()
                .id(recipeReview.getId())
                .content(recipeReview.getContent())
                .rating(recipeReview.getRating())
                .build();
    }
}
