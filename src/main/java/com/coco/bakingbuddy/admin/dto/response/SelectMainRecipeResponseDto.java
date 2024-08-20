package com.coco.bakingbuddy.admin.dto.response;

import com.coco.bakingbuddy.admin.domain.MainRecipe;
import com.coco.bakingbuddy.ingredient.dto.response.IngredientResponseDto;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import com.coco.bakingbuddy.recipe.dto.response.WriterResponseDto;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@Data

public class SelectMainRecipeResponseDto {
    private Long recipeId;
    private Long mainRecipeId;
    private String name;
    private Long dirId;
    private String description;
    private String username;
    private UUID writerUuid;
    private String profileImageUrl;
    private boolean openYn; // 공개 여부 True - Open
    private List<IngredientResponseDto> ingredients; // JPA
    private List<RecipeStep> recipeSteps; // JPA
    private List<Tag> tags; // JPA
    private String recipeImageUrl;
    private Integer time; // 소요시간
    private String level; // 난이도
    private Integer likeCount;
    private boolean userLiked;
    private Integer servings;
    private WriterResponseDto writer;
    private boolean isCurrent;

    public static SelectMainRecipeResponseDto fromEntity(MainRecipe mainRecipe) {
        Recipe recipe = mainRecipe.getRecipe();
        return SelectMainRecipeResponseDto.builder()
                .mainRecipeId(mainRecipe.getId())
                .recipeId(recipe.getId())
                .dirId(recipe.getDirectory().getId())
                .name(recipe.getName())
                .description(recipe.getDescription())
                .time(recipe.getTime())
                .level(recipe.getLevel())
                .recipeImageUrl(recipe.getRecipeImageUrl())
                .likeCount(recipe.getLikeCount())
                .username(recipe.getUser().getUsername())
                .profileImageUrl(recipe.getUser().getProfileImageUrl())
                .isCurrent(mainRecipe.isCurrent())
                .build();
    }
}
