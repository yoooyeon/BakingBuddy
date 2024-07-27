package com.coco.bakingbuddy.search.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AutoCompletePreviewDto {
    private String name;
    private Long recipeId;
    private String imageUrl;

}
