package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Recipe;
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

    @NotNull(message = "userId가 빈 값일 수 없습니다.")
    private Long userId;

    private String memo;

    @Pattern(regexp = "^[yYnN]$", message = "openYn은 y, Y, n, N 중 하나여야 합니다.")
    private String openYn; // 공개 여부 True - Open

    @NotEmpty(message = "ingredients가 빈 값일 수 없습니다.")
    private List<String> ingredients; // JPA

    @NotNull(message = "time이 빈 값일 수 없습니다.")
    @Positive(message = "time은 양수여야 합니다.")
    private Integer time; // 소요시간

    @NotBlank(message = "level이 빈 값일 수 없습니다.")
    private String level; // 난이도

    @NotEmpty(message = "tags가 빈 값일 수 없습니다.")
    private List<String> tags;
    private String recipeImageUrl;

    public static Recipe toEntity(CreateRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .memo(dto.getMemo())
                .time(dto.getTime())
                .level(dto.getLevel())
                .openYn(dto.getOpenYn())
                .recipeImageUrl(dto.getRecipeImageUrl())
                .build();
    }
}
