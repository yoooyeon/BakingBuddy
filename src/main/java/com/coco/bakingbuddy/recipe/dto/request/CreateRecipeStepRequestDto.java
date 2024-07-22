package com.coco.bakingbuddy.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeStepRequestDto {
    private Long recipeId;
    private Integer stepNumber; // 단계 번호
    private String description; // 단계 설명
    private String stepImage; // 단계 이미지

}
