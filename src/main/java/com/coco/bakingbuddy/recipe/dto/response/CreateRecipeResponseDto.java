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
    private Long dirId;
    private Long id;
    private Long userId;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private String ingredients; // JPA
    private String tags; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도

    public static CreateRecipeResponseDto fromEntity(Recipe recipe) {
        return CreateRecipeResponseDto.builder()
                .id(recipe.getId())
                .dirId(recipe.getDirectory().getId())
                .name(recipe.getName())
                .userId(recipe.getUser().getId())
                .memo(recipe.getMemo())
                .ingredients(recipe.getIngredients())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .build();
    }
}
