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
    private String username;
    private boolean openYn; // 공개 여부 True - Open

    private List<Ingredient> ingredients; // JPA
    private List<Tag> tags; // JPA
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도
    private Integer viewCount; // 조회 수
    private Integer hearts; // 좋아요 수

    public static SelectRecipeResponseDto fromEntity(Recipe recipe) {
        return SelectRecipeResponseDto.builder()
                .id(recipe.getId())
                .dirId(recipe.getDirectory().getId())
                .name(recipe.getName())
                .userId(recipe.getUser().getId())
                .memo(recipe.getMemo())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .recipeImageUrl(recipe.getRecipeImageUrl())
                .viewCount(recipe.getViewCount())
                .hearts(recipe.getHearts())
                .build();
    }
}
