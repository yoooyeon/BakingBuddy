package com.coco.bakingbuddy.review.dto.response;

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
public class CreateRecipeReviewResponseDto {
    private Long id;
    private SelectUserResponseDto user;
    private String content;
    public static CreateRecipeReviewResponseDto fromEntity(RecipeReview recipeReview) {
        return CreateRecipeReviewResponseDto.builder()
                .id(recipeReview.getId())
                .content(recipeReview.getContent())
                .build();
    }
}
