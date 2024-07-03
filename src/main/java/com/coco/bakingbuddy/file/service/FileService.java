package com.coco.bakingbuddy.file.service;

import com.coco.bakingbuddy.file.dto.request.ImageFileCreateRequestDto;
import com.coco.bakingbuddy.file.repository.ImageFileRepository;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {
    private final String UPLOAD_PATH = "UserProfile";
    private final String BUCKET_NAME = "baking-buddy-bucket";
    private final Storage storage;
    private final ImageFileRepository fileRepository;


    public void uploadImageFile(Long userId, MultipartFile imageFile) {
        String originalName = imageFile.getOriginalFilename();
        String ext = imageFile.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = uuid + "_" + originalName;
        String uploadPath = UPLOAD_PATH + uuid;
        try {
            // GCS에 이미지 파일 업로드
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                    .setContentType(ext)
                    .build();
            storage.create(blobInfo, imageFile.getInputStream());

            // 이미지 파일 메타 데이터 저장
            ImageFileCreateRequestDto dto = ImageFileCreateRequestDto.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
                    .userId(userId)
                    .uploadPath(uploadPath)
                    .build();

            fileRepository.save(dto.toEntity());

        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to save profile image: " + e.getMessage());
        }
    }

}
