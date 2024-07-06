package com.coco.bakingbuddy.file.dto.request;

import com.coco.bakingbuddy.file.domain.ImageFile;
import com.coco.bakingbuddy.file.domain.RecipeImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class RecipeImageFileCreateRequestDto {
    private String originalName;
    private String ext;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Long recipeId;

    public RecipeImageFile toEntity() {
        return RecipeImageFile.builder()
                .originalName(originalName)
                .ext(ext)
                .uuid(uuid)
                .uploadPath(uploadPath)
                .fileName(fileName)
                .recipeId(recipeId)
                .build();
    }
}
