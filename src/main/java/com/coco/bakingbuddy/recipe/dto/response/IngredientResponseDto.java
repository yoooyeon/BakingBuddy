package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.Unit;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class IngredientResponseDto {
    private String name;
    private Unit unit;
    private BigDecimal amount;
    private int servings;
}
