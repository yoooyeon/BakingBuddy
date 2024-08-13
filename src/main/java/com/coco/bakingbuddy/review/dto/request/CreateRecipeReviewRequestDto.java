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
    private Long recipeId;
    private String content;

    public static RecipeReview toEntity(CreateRecipeReviewRequestDto dto) {
        return RecipeReview.builder()
                .content(dto.getContent())
                .build();
    }
}
