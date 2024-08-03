package com.coco.bakingbuddy.recipe.dto.request;

import com.coco.bakingbuddy.recipe.domain.Directory;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateDirectoryRequestDto {
    @NotBlank(message = "이름이 빈 값일 수 없습니다.")
    private String name;

    public static Directory toEntity(CreateDirectoryRequestDto dto) {
        return Directory.builder()
                .name(dto.getName())
                .build();
    }
}
