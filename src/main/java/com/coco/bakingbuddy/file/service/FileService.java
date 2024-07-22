package com.coco.bakingbuddy.file.service;

import com.coco.bakingbuddy.file.dto.request.ImageFileCreateRequestDto;
import com.coco.bakingbuddy.file.dto.request.RecipeImageFileCreateRequestDto;
import com.coco.bakingbuddy.file.dto.request.RecipeStepImageFileCreateRequestDto;
import com.coco.bakingbuddy.file.dto.response.ImageFileCreateResponseDto;
import com.coco.bakingbuddy.file.dto.response.RecipeImageFileCreateResponseDto;
import com.coco.bakingbuddy.file.repository.ImageFileRepository;
import com.coco.bakingbuddy.file.repository.RecipeImageFileRepository;
import com.coco.bakingbuddy.file.repository.RecipeStepImageFileRepository;
import com.coco.bakingbuddy.file.repository.RecipeStepRepository;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class FileService {
    private final String UPLOAD_PATH = "UserProfile/";
    private final String RECIPE_UPLOAD_PATH = "RecipeProfile/";
    private final String RECIPE_STEP_UPLOAD_PATH = "RecipeStep/";
    private final String BUCKET_NAME = "baking-buddy-bucket";
    private final Storage storage;
    private final ImageFileRepository imageFileRepository;
    private final UserService userService;
    private final RecipeRepository recipeRepository;
    private final RecipeImageFileRepository recipeImageFileRepository;
    private final RecipeStepRepository recipeStepRepository;
    private final RecipeStepImageFileRepository recipeStepImageFileRepository;


    @Transactional

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

    @Transactional
    public RecipeImageFileCreateResponseDto uploadRecipeImageFile(Long recipeId, MultipartFile multiPartFile) {
        String originalName = multiPartFile.getOriginalFilename();
        String ext = multiPartFile.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = RECIPE_UPLOAD_PATH + uuid + "_" + originalName;
        String uploadPath = RECIPE_UPLOAD_PATH + uuid;
        try {
            // GCS에 이미지 파일 업로드
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                    .setContentType(ext)
                    .build();
            storage.create(blobInfo, multiPartFile.getInputStream());

            // 이미지 파일 메타 데이터 저장
            RecipeImageFileCreateRequestDto dto = RecipeImageFileCreateRequestDto.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
                    .recipeId(recipeId)
                    .uploadPath(uploadPath)
                    .build();
            // 유저 도메인에 파일 경로 저장
            Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
            recipe.updateImage("https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName);
            recipeRepository.save(recipe);
            log.info("userUpdateProfile:" + "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName);

            return RecipeImageFileCreateResponseDto.fromEntity(recipeImageFileRepository.save(dto.toEntity()));

        } catch (IOException e) {
            // 파일 업로드 중 오류 발생 시 처리
            e.printStackTrace();
            throw new RuntimeException("Failed to save profile image: " + e.getMessage());
        }
    }

    @Transactional
    public String uploadRecipeStepImage(MultipartFile stepImageFile) {
        String originalName = stepImageFile.getOriginalFilename();
        String ext = stepImageFile.getContentType();
        String uuid = UUID.randomUUID().toString();
        String fileName = RECIPE_STEP_UPLOAD_PATH + uuid + "_" + originalName;
        String uploadPath = RECIPE_STEP_UPLOAD_PATH + uuid;

        try {
            // Upload image file to GCS
            BlobInfo blobInfo = BlobInfo.newBuilder(BUCKET_NAME, fileName)
                    .setContentType(ext)
                    .build();
            storage.create(blobInfo, stepImageFile.getInputStream());

            // Save image file metadata
            RecipeStepImageFileCreateRequestDto dto = RecipeStepImageFileCreateRequestDto.builder()
                    .originalName(originalName)
                    .ext(ext)
                    .uuid(uuid)
                    .fileName(fileName)
//                    .recipeStepId(recipeStepId)
                    .uploadPath(uploadPath)
                    .build();
            log.info("Uploaded step image: " + "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName);
            return "https://storage.googleapis.com/" + BUCKET_NAME + "/" + fileName;

        } catch (IOException e) {
            throw new RuntimeException("Failed to save step image: " + e.getMessage());
        }
    }

}
