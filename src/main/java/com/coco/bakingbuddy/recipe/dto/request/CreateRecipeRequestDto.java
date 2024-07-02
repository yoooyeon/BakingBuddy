package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "dirId가 빈 값일 수 없습니다.")

    private Long dirId;
    @NotBlank(message = "userId 가 빈 값일 수 없습니다.")
    private Long userId;
    private String memo;

    private String openYn; // 공개 여부 True - Open
    @NotBlank(message = "ingredients 가 빈 값일 수 없습니다.")

    private List<String> ingredients; // JPA
    @NotBlank(message = "time 가 빈 값일 수 없습니다.")

    private Integer time; // 소요시간
    @NotBlank(message = "level 가 빈 값일 수 없습니다.")
    private String level; // 난이도
    @NotBlank(message = "tags 가 빈 값일 수 없습니다.")
    private List<String> tags;

    public static Recipe toEntity(CreateRecipeRequestDto dto) {
        return Recipe.builder()
                .name(dto.getName())
                .memo(dto.getMemo())
//                .ingredients(dto.getIngredients())
                .time(dto.getTime())
                .level(dto.getLevel())
                .openYn(dto.getOpenYn())
                .build();
    }
}
