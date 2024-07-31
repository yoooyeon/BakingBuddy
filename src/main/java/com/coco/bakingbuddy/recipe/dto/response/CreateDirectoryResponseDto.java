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
public class CreateDirectoryResponseDto {
    private Long id;
    private String name;


    public static CreateDirectoryResponseDto fromEntity(Directory directory) {
        return CreateDirectoryResponseDto.builder()
                .id(directory.getId())
                .name(directory.getName())
                .build();
    }
}
