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
public class RecipeResponseDto {
    private String name;
    private Long id;
    private String description;
    private boolean openYn; // 공개 여부 True - Open
    private String profileImageUrl;

    private String ingredients; // JPA
    private String tags; // JPA
    private String recipeImageUrl;

    private Integer time; // 소요시간
    private String level; // 난이도
    private Integer likeCount;


    public static RecipeResponseDto fromEntity(Recipe recipe) {
        return RecipeResponseDto.builder()
                .id(recipe.getId())
                .name(recipe.getName())
                .recipeImageUrl(recipe.getRecipeImageUrl())
                .description(recipe.getDescription())
//                .ingredients(recipe.getIngredients())
                .time(recipe.getTime())
                .likeCount(recipe.getLikeCount())

                .level(recipe.getLevel())
                .build();
    }
}
