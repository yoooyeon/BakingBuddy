package com.coco.bakingbuddy.review.dto.request;

import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.product.dto.request.EditProductRequestDto;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeReviewRequestDto {
    private String content;
    private int rating; // 1-5점 사이의 평점

    public static RecipeReview toEntity(CreateRecipeReviewRequestDto dto) {
        return RecipeReview.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
    }
}
