package com.coco.bakingbuddy.file.dto.response;

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
public class RecipeImageFileCreateResponseDto {
    private String originalName;
    private String ext;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Long recipeId;

    public static RecipeImageFileCreateResponseDto fromEntity(RecipeImageFile file) {
        return RecipeImageFileCreateResponseDto.builder()
                .originalName(file.getOriginalName())
                .ext(file.getExt())
                .uuid(file.getUuid())
                .uploadPath(file.getUploadPath())
                .fileName(file.getFileName())
                .recipeId(file.getRecipeId())
                .build();
    }
}
