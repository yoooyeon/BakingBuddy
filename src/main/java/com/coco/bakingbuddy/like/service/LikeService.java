package com.coco.bakingbuddy.like.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.like.repository.LikeRepository;
import com.coco.bakingbuddy.like.dto.response.LikeResponseDto;
import com.coco.bakingbuddy.like.domain.Like;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.socket.MessagingService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final MessagingService messagingService;

    @Transactional
    public LikeResponseDto likeRecipe(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Like> existingLike = likeRepository.findByRecipeAndUser(recipe, user);
        boolean isLiked;
        if (!existingLike.isPresent()) {
            Like newLike = Like.builder()
                    .recipe(recipe)
                    .user(user)
                    .build();
            likeRepository.save(newLike);
            recipe.increaseLikeCount(newLike);
            isLiked = true;
        } else {
            Like like = existingLike.get();
            likeRepository.delete(like);
            recipe.decreaseLikeCount(like);
            isLiked = false;
        }

        Recipe save = recipeRepository.save(recipe);
        messagingService.broadcastLikeUpdate(new LikeResponseDto(isLiked, save.getLikeCount()));
        return LikeResponseDto.builder()
                .userLiked(isLiked)
                .likeCount(save.getLikeCount())
                .build();
    }

    @Transactional
    public LikeResponseDto unlikeRecipe(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Like> existingLike = likeRepository.findByRecipeAndUser(recipe, user);
        if (existingLike.isPresent()) {
            Like like = existingLike.get();
            likeRepository.delete(like);
            recipe.decreaseLikeCount(like);
            recipeRepository.save(recipe);
        }

        return LikeResponseDto.builder()
                .userLiked(false)
                .likeCount(recipe.getLikeCount())
                .build();
    }

    public LikeResponseDto status(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Like> existingLike = likeRepository.findByRecipeAndUser(recipe, user);
        if (existingLike.isPresent()) {
            return LikeResponseDto.builder()
                    .userLiked(true)
                    .build();
        }
        return LikeResponseDto.builder()
                .userLiked(false)
                .build();
    }
}

