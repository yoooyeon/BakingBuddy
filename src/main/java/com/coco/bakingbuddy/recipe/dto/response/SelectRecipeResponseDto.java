package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
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
    private String description;
    private String username;
    private String profileImageUrl;
    private boolean openYn; // 공개 여부 True - Open
    private List<Ingredient> ingredients; // JPA
    private List<RecipeStep> recipeSteps; // JPA
    private List<Tag> tags; // JPA
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도
    private Integer likeCount;

    public static SelectRecipeResponseDto fromEntity(Recipe recipe) {
        return SelectRecipeResponseDto.builder()
                .id(recipe.getId())
                .dirId(recipe.getDirectory().getId())
                .name(recipe.getName())
                .userId(recipe.getUser().getId())
                .description(recipe.getDescription())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .recipeImageUrl(recipe.getRecipeImageUrl())
                .likeCount(recipe.getLikeCount())
                .build();
    }
}
