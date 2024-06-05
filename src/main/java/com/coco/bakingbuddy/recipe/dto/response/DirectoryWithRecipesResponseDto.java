package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.tag.domain.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DirectoryWithRecipesResponseDto {

    private Long dirId;
    private String dirName;
    private List<RecipeResponseDto> recipes;
    public static DirectoryWithRecipesResponseDto fromEntity(Directory dir) {
        return DirectoryWithRecipesResponseDto.builder()
                .dirId(dir.getId())
                .dirName(dir.getName())
                .build();
    }
}
