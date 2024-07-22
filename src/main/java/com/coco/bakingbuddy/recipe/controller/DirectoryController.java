package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.CreateDirectoryRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;


@RequestMapping("/api/directories")
@RequiredArgsConstructor
@Controller
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("users/{userId}")
    public ResponseEntity<SuccessResponse<List<SelectDirectoryResponseDto>>> selectByUserId(
            @PathVariable("userId") Long userId) {
        return toResponseEntity("사용자 디렉토리 조회 성공", directoryService.selectByUserId(userId));
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<List<SelectDirectoryResponseDto>>> selectById(@PathVariable("id") Long id) {
        return toResponseEntity("디렉토리 이름 조회 성공", directoryService.selectById(id));
    }

    @PostMapping
    @ResponseBody
    public ResponseEntity<SuccessResponse<CreateDirectoryResponseDto>> create(@Valid @RequestBody CreateDirectoryRequestDto dto) {
        return toResponseEntity("디렉토리 생성 성공", directoryService.create(dto));
    }
}
