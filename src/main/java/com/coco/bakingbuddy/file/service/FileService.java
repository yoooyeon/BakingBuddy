package com.coco.bakingbuddy.file.service;

import com.coco.bakingbuddy.file.domain.ImageFile;
import com.coco.bakingbuddy.file.domain.RecipeImageFile;
import com.coco.bakingbuddy.file.domain.RecipeStepImageFile;
import com.coco.bakingbuddy.file.repository.ImageFileRepository;
import com.coco.bakingbuddy.file.repository.RecipeImageFileRepository;
import com.coco.bakingbuddy.file.repository.RecipeStepImageFileRepository;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static com.coco.bakingbuddy.global.error.ErrorCode.MAX_UPLOAD_SIZE_EXCEEDED;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {
    private final Storage storage;
    private final ImageFileRepository imageFileRepository;
    private final RecipeImageFileRepository recipeImageFileRepository;
    private final RecipeStepImageFileRepository recipeStepImageFileRepository;

    private final String UPLOAD_PATH = "UserProfile/";
    private final String RECIPE_UPLOAD_PATH = "RecipeProfile/";
    private final String RECIPE_STEP_UPLOAD_PATH = "RecipeStep/";
    private final String BUCKET_NAME = "baking-buddy-bucket/";
    private final String STORAGE_URL = "https://storage.googleapis.com/";

    @Transactional
    public String uploadUserProfileImageFile(Long userId, MultipartFile multiPartFile) {
        return uploadFile(multiPartFile, UPLOAD_PATH, (fileName, uuid, originalName, ext) -> {
            imageFileRepository.save(ImageFile.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
                    .userId(userId)
                    .uploadPath(UPLOAD_PATH + uuid)
                    .build());
        });
    }

    @Transactional
    public String uploadRecipeImageFile(Long recipeId, MultipartFile multiPartFile) {
        return uploadFile(multiPartFile, RECIPE_UPLOAD_PATH, (fileName, uuid, originalName, ext) -> {
            recipeImageFileRepository.save(RecipeImageFile.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
                    .recipeId(recipeId)
                    .uploadPath(RECIPE_UPLOAD_PATH + uuid)
                    .build());
        });
    }

    @Transactional
    public String uploadRecipeStepImage(MultipartFile stepImageFile) {
        return uploadFile(stepImageFile, RECIPE_STEP_UPLOAD_PATH, (fileName, uuid, originalName, ext) -> {
            recipeStepImageFileRepository.save(
                    RecipeStepImageFile.builder()
                            .originalName(originalName)
                            .ext(ext)
                            .uuid(uuid)
                            .fileName(fileName)
                            .uploadPath(RECIPE_STEP_UPLOAD_PATH + uuid)
                            .build());
        });
    }

    private String uploadFile(MultipartFile file, String uploadPath, FileMetadataHandler handler) {
        String originalName = file.getOriginalFilename();
        String ext = file.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = uploadPath + uuid + "_" + originalName;

        try {
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                    .setContentType(ext)
                    .build();
            storage.create(blobInfo, file.getInputStream());
            handler.handle(fileName, uuid, originalName, ext);
            return makeImageUrl(fileName);

        } catch (IOException e) {
            throw new RuntimeException("Failed to save image: " + e.getMessage());
        } catch (MaxUploadSizeExceededException e) {
            throw new CustomException(MAX_UPLOAD_SIZE_EXCEEDED);
        }
    }

    private String makeImageUrl(String fileName) {
        return STORAGE_URL + BUCKET_NAME + fileName;
    }

    public void deleteImages(List<Long> imageIds) {
        for (Long imageId : imageIds) {
            log.info(">>>deleteImages{}", imageId);
        }
        // TODO: 이미지 삭제 처리
    }

    @FunctionalInterface
    private interface FileMetadataHandler {
        void handle(String fileName, String uuid, String originalName, String ext);
    }
}
