package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class EditRecipeRequestDto {
    private Long id;
    private String name;
    private Long dirId;
    private Long userId;
    private String memo;
    private boolean openYn; // 공개 여부 True - Open

    private List<String> ingredients; // JPA

    private Integer time; // 소요시간
    private Integer level; // 난이도
    private List<String> tags;

    public static Recipe toEntity(EditRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .memo(dto.getMemo())
//                .ingredients(dto.getIngredients())
                .time(dto.getTime())
                .level(dto.getLevel())
                .build();
    }
}

