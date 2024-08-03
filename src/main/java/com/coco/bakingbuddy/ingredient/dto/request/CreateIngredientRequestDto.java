package com.coco.bakingbuddy.ingredient.dto.request;

import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.ingredient.domain.Unit;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateIngredientRequestDto {
    @NotBlank(message = "재료 이름이 빈 값일 수 없습니다.")
    private String name;

    //    @NotBlank(message = "단위(Unit)가 빈 값일 수 없습니다.")
    private String unit;  // Unit enum이 아닌 String으로 변경
    @NotNull(message = "재료 양(amount)이 빈 값일 수 없습니다.")
    private BigDecimal amount;
    //    @NotBlank
    @NotEmpty(message = "단위(Unit)가 빈 값일 수 없습니다.")
    private String unitDisplayName;

    public static IngredientRecipe toEntity(CreateIngredientRequestDto dto) {
        return IngredientRecipe.builder()
                .amount(dto.getAmount())
                .unit(Unit.from(dto.getUnitDisplayName()))
                .build();
    }
}
