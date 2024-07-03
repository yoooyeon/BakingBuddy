package com.coco.bakingbuddy.file.controller;

import com.coco.bakingbuddy.file.dto.response.ImageFileCreateResponseDto;
import com.coco.bakingbuddy.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/files")
@Controller
public class FileController {
    private final FileService fileService;

    @ResponseBody
    @PostMapping("/users/{userId}")
    public ImageFileCreateResponseDto upload(
            @PathVariable("userId") Long userId,
            @RequestParam("nickname") String nickname,
            @RequestParam("profileImage") MultipartFile profileImage
    ) {
        return fileService.uploadImageFile(userId, nickname, profileImage);
    }

}
