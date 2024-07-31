package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.CreateDirectoryRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;


@RequestMapping("/api/directories")
@RequiredArgsConstructor
@RestController
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("users")
    public ResponseEntity<SuccessResponse<List<SelectDirectoryResponseDto>>> selectByUserId(
            @AuthenticationPrincipal User user) {
        return toResponseEntity("사용자 디렉토리 조회 성공", directoryService.selectByUserId(user.getId()));
    }

    @GetMapping("{id}")
    public ResponseEntity<SuccessResponse<List<SelectDirectoryResponseDto>>> selectById(@PathVariable("id") Long id) {
        return toResponseEntity("디렉토리 이름 조회 성공", directoryService.selectById(id));
    }

    @PostMapping("users")
    public ResponseEntity<SuccessResponse<CreateDirectoryResponseDto>>
    create(@Valid @RequestBody CreateDirectoryRequestDto dto,
           @AuthenticationPrincipal User user) {
        return toResponseEntity("디렉토리 생성 성공", directoryService.create(user.getId(), dto));
    }
}
