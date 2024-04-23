package com.coco.bakingbuddy.recipe.dto.response;

import com.coco.bakingbuddy.recipe.domain.Directory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectDirectoryResponseDto {
    private Long id;
    private String name;

    public static SelectDirectoryResponseDto fromEntity(Directory directory) {
        return SelectDirectoryResponseDto.builder()
                .id(directory.getId())
                .name(directory.getName())
                .build();
    }
}
