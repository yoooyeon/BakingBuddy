package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.Like;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.repository.LikeRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;

    @Transactional
    public void likeRecipe(Long recipeId, Long userId) {
        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() -> new CustomException(ErrorCode.RECIPE_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        Optional<Like> existingLike = likeRepository.findByRecipeAndUser(recipe, user);
        if (!existingLike.isPresent()) {
            Like newLike = Like.builder()
                    .recipe(recipe)
                    .user(user)
                    .build();

            Like like = likeRepository.save(newLike);
            recipe.increaseLikeCount(like);

            // 다음과 같이 저장하지 않아도 영속성 컨텍스트에 의해 관리되지만
            // 명시성, 플러시 모드와 전략의 불확실성 때문에 작성했다.
            recipeRepository.save(recipe);
        }
    }

    @Transactional
    public void unlikeRecipe(Long recipeId, Long userId) {
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
    }
}
