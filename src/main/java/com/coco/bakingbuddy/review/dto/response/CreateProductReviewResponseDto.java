package com.coco.bakingbuddy.review.dto.response;

import com.coco.bakingbuddy.review.domain.ProductReview;
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
public class CreateProductReviewResponseDto {
    private Long id;
    private SelectUserResponseDto user;
    private String content;
    public static CreateProductReviewResponseDto fromEntity(ProductReview productReview) {
        return CreateProductReviewResponseDto.builder()
                .id(productReview.getId())
                .content(productReview.getContent())
                .build();
    }
}
