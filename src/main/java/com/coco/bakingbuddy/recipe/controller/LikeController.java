package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.recipe.dto.request.LikeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.LikeResponseDto;
import com.coco.bakingbuddy.recipe.service.LikeService;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RequestMapping("/api/likes")
@RestController
public class LikeController {
    private final LikeService likeService;

    @PostMapping("recipes/{id}")
    public ResponseEntity<SuccessResponse<LikeResponseDto>>
    like(@PathVariable("id") Long recipeId, @AuthenticationPrincipal User user) {
        LikeResponseDto result = likeService.likeRecipe(recipeId, user.getId());
        return toResponseEntity("좋아요 처리 성공", result);
    }

    @DeleteMapping("recipes/{id}")
    public ResponseEntity<SuccessResponse<LikeResponseDto>>
    unlike(@PathVariable("id") Long recipeId,@AuthenticationPrincipal User user) {
        LikeResponseDto result = likeService.unlikeRecipe(recipeId, user.getId());
        return toResponseEntity("좋아요 취소 처리 성공", result);
    }

    @GetMapping("status")
    public ResponseEntity<SuccessResponse<LikeResponseDto>>
    status(@RequestParam("recipeId") Long recipeId,@AuthenticationPrincipal User user) {
        LikeResponseDto result = likeService.status(recipeId, user.getId());
        return toResponseEntity("좋아요 상태 조회", result);
    }
}
