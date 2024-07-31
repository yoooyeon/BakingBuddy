package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.ingredient.dto.request.CreateIngredientRequestDto;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.domain.RecipeStep;
import jakarta.validation.constraints.*;
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
    @NotBlank(message = "이름이 빈 값일 수 없습니다.")
    private String name;

    @NotNull(message = "dirId가 빈 값일 수 없습니다.")
    private Long dirId;
    @NotNull(message = "레시피 설명이 빈 값일 수 없습니다.")
    private String description;

    @Pattern(regexp = "^[yYnN]$", message = "openYn은 y, Y, n, N 중 하나여야 합니다.")
    private String openYn; // 공개 여부 True - Open

    @NotEmpty(message = "ingredients가 빈 값일 수 없습니다.")
    private List<CreateIngredientRequestDto> ingredients; // JPA

    //    @NotEmpty(message = "조리 단계가 빈 값일 수 없습니다.")
    private List<RecipeStep> recipeSteps;
    @NotNull(message = "time이 빈 값일 수 없습니다.")
    @Positive(message = "time은 양수여야 합니다.")
    private Integer time; // 소요시간

    @NotBlank(message = "level이 빈 값일 수 없습니다.")
    private String level; // 난이도

    //    @NotEmpty(message = "tags가 빈 값일 수 없습니다.")
    private List<String> tags;
    private String recipeImageUrl;

    @NotNull(message = "기준 단위(인분 등)(servings)이 빈 값일 수 없습니다.")
    private Integer servings;

    public static Recipe toEntity(CreateRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .description(dto.getDescription())
                .time(dto.getTime())
                .level(dto.getLevel())
                .openYn(dto.getOpenYn())
                .recipeImageUrl(dto.getRecipeImageUrl())
                .build();
    }
}
