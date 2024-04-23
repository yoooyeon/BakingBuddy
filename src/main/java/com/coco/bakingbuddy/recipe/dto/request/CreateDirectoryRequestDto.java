package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Directory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateDirectoryRequestDto {
    private String name;

    public static Directory toEntity(CreateDirectoryRequestDto dto) {
        return Directory.builder()
                .name(dto.getName())
                .build();
    }
}
