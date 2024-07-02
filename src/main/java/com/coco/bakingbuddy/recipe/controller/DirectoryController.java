package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.request.CreateDirectoryRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping("/api/directories")
@RequiredArgsConstructor
@Controller
public class DirectoryController {

    private final DirectoryService directoryService;

    @GetMapping("users/{userId}")
    public List<SelectDirectoryResponseDto> selectByUserId(@PathVariable("userId") Long userId) {
        return directoryService.selectByUserId(userId);
    }

    @GetMapping("{id}")
    public List<SelectDirectoryResponseDto> selectById(@PathVariable("id") Long id) {
        return directoryService.selectById(id);
    }

    @PostMapping
    @ResponseBody
    public CreateDirectoryResponseDto create(@Valid @RequestBody CreateDirectoryRequestDto dto) {
        return directoryService.create(dto);
    }
}
