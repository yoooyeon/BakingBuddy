package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/api/directories")
@RequiredArgsConstructor
@RestController
public class DirectoryController {

    private final DirectoryService directoryService;
    @GetMapping("users/{userId}")
    public List<SelectDirectoryResponseDto> selectByUserId(@PathVariable Long userId) {
        return directoryService.selectByUserId(userId);
    }
}
