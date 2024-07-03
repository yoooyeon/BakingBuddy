package com.coco.bakingbuddy.file.dto.request;

import com.coco.bakingbuddy.file.domain.ImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageFileCreateRequestDto {
    private String originalName;
    private String ext;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Long userId;

    public ImageFile toEntity() {
        return ImageFile.builder()
                .originalName(originalName)
                .ext(ext)
                .uuid(uuid)
                .uploadPath(uploadPath)
                .fileName(fileName)
                .userId(userId)
                .build();
    }
}
