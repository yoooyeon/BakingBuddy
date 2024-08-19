package com.coco.bakingbuddy.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateRecipeReportRequestDto {
    private Long recipeId;
    private String reason;
    private String reportType;
}
