package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.LikeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.LikeResponseDto;
import com.coco.bakingbuddy.recipe.service.LikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api/likes")
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<SuccessResponse<LikeResponseDto>> like(@RequestBody LikeRequestDto dto) {
        LikeResponseDto result = likeService.likeRecipe(dto.getRecipeId(), dto.getUserId());
        return SuccessResponse.toResponseEntity("좋아요 처리 성공", result);
    }

    @DeleteMapping
    public ResponseEntity<SuccessResponse<LikeResponseDto>> unlike(@RequestBody LikeRequestDto dto) {
        LikeResponseDto result = likeService.unlikeRecipe(dto.getRecipeId(), dto.getUserId());
        return SuccessResponse.toResponseEntity("좋아요 처리 성공", result);
    }

}
