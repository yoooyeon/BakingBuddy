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
public class SelectRecipeResponseDto {
    private String name;

    public static SelectRecipeResponseDto fromEntity(Recipe recipe) {
        return SelectRecipeResponseDto.builder()
                .name(recipe.getName())
                .build();
    }
}
