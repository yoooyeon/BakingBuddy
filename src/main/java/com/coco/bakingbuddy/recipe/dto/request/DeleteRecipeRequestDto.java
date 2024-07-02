package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeleteRecipeRequestDto {
    @NotNull(message = "id가 빈 값일 수 없습니다.")
    private Long id;

    public static Recipe toEntity(DeleteRecipeRequestDto dto) {
        return Recipe.builder()
                .id(dto.id)
                .build();
    }
}
