package com.coco.bakingbuddy.review.dto.response;

import com.coco.bakingbuddy.review.domain.RecipeReview;
import com.coco.bakingbuddy.user.dto.response.UserNicknameAndProfileDto;
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
    private UserNicknameAndProfileDto user;
    private String content;
    public static SelectRecipeReviewResponseDto fromEntity(RecipeReview recipeReview) {
        return SelectRecipeReviewResponseDto.builder()
                .id(recipeReview.getId())
                .user(UserNicknameAndProfileDto.fromEntity(recipeReview.getUser()))  // User 정보를 변환하여 추가
                .content(recipeReview.getContent())
                .build();
    }
}
