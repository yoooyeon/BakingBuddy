package com.coco.bakingbuddy.recipe.dto.request;

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
public class CreateRecipeRequestDto {
    private String name;
    private Long dirId;
    private Long userId;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private String ingredients; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도
    private List<Tag> tags;

    public static Recipe toEntity(CreateRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .memo(dto.getMemo())
                .ingredients(dto.getIngredients())
                .time(dto.getTime())
                .level(dto.getLevel())
                .build();
    }
}
