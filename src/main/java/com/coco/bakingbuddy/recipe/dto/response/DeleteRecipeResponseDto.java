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
public class DeleteRecipeResponseDto {
    private String name;

    public static DeleteRecipeResponseDto fromEntity(Recipe recipe) {
        return DeleteRecipeResponseDto.builder()
                .name(recipe.getName())
                .build();
    }
}
