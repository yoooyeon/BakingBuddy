package com.coco.bakingbuddy.file.dto.response;

import com.coco.bakingbuddy.file.domain.UserProfileImageFile;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ImageFileCreateResponseDto {
    private String originalName;
    private String ext;
    private String uuid;
    private String uploadPath;
    private String fileName;
    private Long userId;

    public static ImageFileCreateResponseDto fromEntity(UserProfileImageFile file) {
        return ImageFileCreateResponseDto.builder()
                .originalName(file.getOriginalName())
                .ext(file.getExt())
                .uuid(file.getUuid())
                .uploadPath(file.getUploadPath())
                .fileName(file.getFileName())
                .userId(file.getUser().getId())
                .build();
    }
}
