package com.coco.bakingbuddy.recipe.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LikeRequestDto {
    private Long userId;
    private Long recipeId;
}
