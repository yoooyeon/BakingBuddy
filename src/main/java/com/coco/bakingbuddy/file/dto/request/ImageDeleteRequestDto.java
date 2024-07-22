package com.coco.bakingbuddy.file.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageDeleteRequestDto {
    private List<Long> imageIds;
}
