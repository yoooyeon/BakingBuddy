package com.coco.bakingbuddy.review.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.product.domain.Product;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.review.domain.ProductReview;
import com.coco.bakingbuddy.review.domain.RecipeReview;
import com.coco.bakingbuddy.review.dto.request.CreateRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.request.EditRecipeReviewRequestDto;
import com.coco.bakingbuddy.review.dto.response.CreateRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.dto.response.SelectRecipeReviewResponseDto;
import com.coco.bakingbuddy.review.repository.RecipeReviewRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
public class RecipeReviewService {
    private final RecipeReviewRepository recipeReviewRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;
    public List<SelectRecipeReviewResponseDto> selectAll() {
        return recipeReviewRepository.findAll().stream().map(SelectRecipeReviewResponseDto::fromEntity)
                .collect(Collectors.toList());
    }

    public SelectRecipeReviewResponseDto selectById(Long reviewId, User user) {
        RecipeReview review = recipeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(RECIPE_REVIEW_NOT_FOUND));
        return SelectRecipeReviewResponseDto.fromEntity(review);
    }

    public CreateRecipeReviewResponseDto create(Long userId, CreateRecipeReviewRequestDto dto) {
        RecipeReview entity = CreateRecipeReviewRequestDto.toEntity(dto);
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Recipe recipe = recipeRepository.findById(dto.getRecipeId()).orElseThrow(() -> new CustomException(RECIPE_REVIEW_NOT_FOUND));
        entity.setUser(user);
        entity.setRecipe(recipe);
        RecipeReview save = recipeReviewRepository.save(entity);
        CreateRecipeReviewResponseDto result = CreateRecipeReviewResponseDto.fromEntity(save);
        result.setUser(SelectUserResponseDto.fromEntity(user));
        return result;
    }

    public CreateRecipeReviewResponseDto edit(EditRecipeReviewRequestDto dto, User user) {
        RecipeReview review = recipeReviewRepository.findById(dto.getId())
                .orElseThrow(() -> new CustomException(RECIPE_REVIEW_NOT_FOUND));
        if (!review.getUser().equals(user)) {
            throw new CustomException(REVIEW_USER_MISMATCH);
        }
        review = EditRecipeReviewRequestDto.toEntity(dto);
        recipeReviewRepository.save(review);
        return CreateRecipeReviewResponseDto.fromEntity(review);
    }

    public void delete(Long reviewId, User user) {
        RecipeReview review = recipeReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(RECIPE_REVIEW_NOT_FOUND));
        if (!review.getUser().equals(user)) {
            throw new CustomException(REVIEW_USER_MISMATCH);
        }
        recipeReviewRepository.delete(review);
    }
}

