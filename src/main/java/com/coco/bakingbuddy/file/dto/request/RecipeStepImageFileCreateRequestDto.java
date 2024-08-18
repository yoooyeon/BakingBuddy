package com.coco.bakingbuddy.file.dto.request;

import com.coco.bakingbuddy.file.domain.RecipeStepImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RecipeStepImageFileCreateRequestDto {
    private String originalName;
    private String ext;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Long recipeStepId;

    public RecipeStepImageFile toEntity() {
        return RecipeStepImageFile.builder()
                .originalName(originalName)
                .ext(ext)
                .uuid(uuid)
                .uploadPath(uploadPath)
                .fileName(fileName)
                .build();
    }
}
