package com.coco.bakingbuddy.recipe.dto.response;

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
public class SelectRecipeResponseDto {
    private String name;
    private Long dirId;
    private Long id;
    private Long userId;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private List<Ingredient> ingredients; // JPA
    private List<Tag> tags; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도

    public static SelectRecipeResponseDto fromEntity(Recipe recipe) {
        return SelectRecipeResponseDto.builder()
                .id(recipe.getId())
                .dirId(recipe.getDirectory().getId())
                .name(recipe.getName())
                .userId(recipe.getUser().getId())
                .memo(recipe.getMemo())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .build();
    }
}
