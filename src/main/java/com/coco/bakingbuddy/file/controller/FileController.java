package com.coco.bakingbuddy.file.controller;

import com.coco.bakingbuddy.file.dto.request.ImageDeleteRequestDto;
import com.coco.bakingbuddy.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/files")
@RestController
public class FileController {
    private final FileService fileService;

//    @PostMapping("/users/{userId}")
//    public ResponseEntity<SuccessResponse<ImageFileCreateResponseDto>> upload(
//            @PathVariable("userId") Long userId,
//            @RequestParam("nickname") String nickname,
//            @RequestParam("profileImage") MultipartFile profileImage) {
//        return toResponseEntity("프로필 수정 완료", fileService.uploadImageFile(userId, nickname, profileImage));
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteImage(@RequestBody ImageDeleteRequestDto request) {
        fileService.deleteImages(request.getImageIds());
        return ResponseEntity.ok("이미지 삭제 성공");
    }

}
