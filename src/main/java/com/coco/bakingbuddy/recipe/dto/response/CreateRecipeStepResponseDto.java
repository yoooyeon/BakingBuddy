package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeStepResponseDto {
    private Long id;
    private Long recipeId;
    private Integer stepNumber; // 단계 번호
    private String description; // 단계 설명
    private String stepImage; // 단계 이미지

    public static CreateRecipeStepResponseDto fromEntity(RecipeStep recipeStep) {
        return CreateRecipeStepResponseDto.builder()
                .id(recipeStep.getId())
                .recipeId(recipeStep.getRecipe().getId())
                .stepNumber(recipeStep.getStepNumber())
                .description(recipeStep.getDescription())
                .stepImage(recipeStep.getStepImage())
                .build();
    }
}
