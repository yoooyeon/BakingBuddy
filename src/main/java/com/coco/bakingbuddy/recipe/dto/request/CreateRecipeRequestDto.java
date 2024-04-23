package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeRequestDto {
    private String name;
    private Long dirId;
    private Long userId;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private String ingredients; // JPA
    private String tags; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도

    public static Recipe toEntity(CreateRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .dirId(dto.getDirId())
                .name(dto.getName())
                .userId(dto.getUserId())
                .memo(dto.getMemo())
                .ingredients(dto.getIngredients())
                .tags(dto.getTags())
                .time(dto.getTime())
                .level(dto.getLevel())
                .build();
    }
}
