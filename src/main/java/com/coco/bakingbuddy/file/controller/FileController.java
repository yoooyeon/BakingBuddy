package com.coco.bakingbuddy.file.controller;

import com.coco.bakingbuddy.file.dto.response.ImageFileCreateResponseDto;
import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RequestMapping("/api/files")
@Controller
public class FileController {
    private final FileService fileService;

    @ResponseBody
    @PostMapping("/users/{userId}")
    public ResponseEntity<SuccessResponse<ImageFileCreateResponseDto>> upload(
            @PathVariable("userId") Long userId,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImage") MultipartFile profileImage) {
        return toResponseEntity("프로필 수정 완료", fileService.uploadImageFile(userId, nickname, profileImage));
    }

//    @ResponseBody
//    @PostMapping("/recipes/{recipeId}")
//    public RecipeImageFileCreateResponseDto upload(
//            @PathVariable("recipeId") Long recipeId,
//            @RequestParam("recipeImage") MultipartFile recipeImage
//    ) {
//        return fileService.uploadRecipeImageFile(recipeId, recipeImage);
//    }

}
