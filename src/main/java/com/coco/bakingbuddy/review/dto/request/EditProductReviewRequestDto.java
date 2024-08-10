package com.coco.bakingbuddy.review.dto.request;

import com.coco.bakingbuddy.review.domain.ProductReview;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EditProductReviewRequestDto {
    private Long id;
    private String content;

    private int rating; // 1-5점 사이의 평점
    public static ProductReview toEntity(EditProductReviewRequestDto dto) {
        return ProductReview.builder()
                .content(dto.getContent())
                .rating(dto.getRating())
                .build();
    }
}
