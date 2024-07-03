package com.coco.bakingbuddy.file.service;

import com.coco.bakingbuddy.file.dto.request.ImageFileCreateRequestDto;
import com.coco.bakingbuddy.file.dto.response.ImageFileCreateResponseDto;
import com.coco.bakingbuddy.file.repository.ImageFileRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
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
    private final String UPLOAD_PATH = "UserProfile/";
    private final String BUCKET_NAME = "baking-buddy-bucket";
    private final Storage storage;
    private final ImageFileRepository imageFileRepository;
    private final UserService userService;

    public ImageFileCreateResponseDto uploadImageFile(Long userId, String nickname, MultipartFile multiPartFile) {
        String originalName = multiPartFile.getOriginalFilename();
        String ext = multiPartFile.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = UPLOAD_PATH + uuid + "_" + originalName;
        String uploadPath = UPLOAD_PATH + uuid;
        try {
            // GCS에 이미지 파일 업로드
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                    .setContentType(ext)
                    .build();
            storage.create(blobInfo, multiPartFile.getInputStream());

            // 이미지 파일 메타 데이터 저장
            ImageFileCreateRequestDto dto = ImageFileCreateRequestDto.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
                    .userId(userId)
                    .uploadPath(uploadPath)
                    .build();
            log.info("uploadPath:" + uploadPath);
            // 유저 도메인에 파일 경로 저장
            User user = userService.selectById(userId);
            user.updateProfile("https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName);
            log.info("userUpdateProfile:" + "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName);
            user.updateNickname(nickname);

            return ImageFileCreateResponseDto.fromEntity(imageFileRepository.save(dto.toEntity()));

        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to save profile image: " + e.getMessage());
        }
    }
}