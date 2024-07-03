package com.coco.bakingbuddy.file.controller;

import com.coco.bakingbuddy.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RequestMapping("/api/files")
@Controller
public class FileController {
    private final FileService fileService;

    @ResponseBody
    @PostMapping("users/{userId}")
    public void upload(@PathVariable("userId") Long userId, MultipartFile file) {
        fileService.uploadImageFile(userId, file);
    }

}
