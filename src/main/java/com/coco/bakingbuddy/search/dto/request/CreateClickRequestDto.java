package com.coco.bakingbuddy.search.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateClickRequestDto {
    private Long id;
    private String clickType;
}
