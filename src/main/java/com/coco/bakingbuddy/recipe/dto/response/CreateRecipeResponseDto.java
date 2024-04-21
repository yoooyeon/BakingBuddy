package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeResponseDto {
    private String name;

    public static CreateRecipeResponseDto fromEntity(Recipe recipe) {
        return CreateRecipeResponseDto.builder()
                .name(recipe.getName())
                .build();
    }
}
