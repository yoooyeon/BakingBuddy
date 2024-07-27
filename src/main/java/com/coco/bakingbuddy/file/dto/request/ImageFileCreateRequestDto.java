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

    public static ImageFile toEntity(ImageFileCreateRequestDto dto) {
        return ImageFile.builder()
                .originalName(dto.getOriginalName())
                .ext(dto.getExt())
                .uuid(dto.getUuid())
                .uploadPath(dto.getUploadPath())
                .fileName(dto.getFileName())
                .userId(dto.getUserId())
                .build();
    }
}
