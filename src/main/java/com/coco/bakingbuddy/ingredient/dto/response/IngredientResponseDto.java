package com.coco.bakingbuddy.ingredient.dto.response;

import com.coco.bakingbuddy.ingredient.domain.Unit;
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
    private Long recipeId;
    private String name;
    private Unit unit;
    private BigDecimal amount;
    private int servings;
    private String unitDisplayName;

}
