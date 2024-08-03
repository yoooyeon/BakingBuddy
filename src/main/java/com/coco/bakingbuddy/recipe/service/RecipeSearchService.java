package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.ingredient.dto.response.IngredientResponseDto;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeQueryDslRepository;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.repository.TagRecipeQueryDslRepository;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeSearchService {
    private final RecipeQueryDslRepository recipeQueryDslRepository;
    private final IngredientRecipeQueryDslRepository ingredientRecipeQueryDslRepository;
    private final TagRecipeQueryDslRepository tagRecipeQueryDslRepository;


    @Transactional(readOnly = true)
    public Page<SelectRecipeResponseDto> selectByTerm(String keyword, Pageable pageable) {
        Page<Recipe> recipePage = recipeQueryDslRepository.findByKeyword(keyword, pageable);
        if (recipePage.isEmpty()) {
            return Page.empty(); // 빈 페이지 객체를 반환
        }
        List<SelectRecipeResponseDto> resultList = new ArrayList<>();
        for (Recipe recipe : recipePage) {
            SelectRecipeResponseDto result = SelectRecipeResponseDto.fromEntity(recipe);
            List<IngredientResponseDto> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(recipe.getId());
            ingredients.forEach(ingredient -> {
                String displayName = ingredient.getUnit().getDisplayName();
                ingredient.setUnitDisplayName(displayName);
            });
            List<Tag> tags = tagRecipeQueryDslRepository.findTagsByRecipeId(recipe.getId());
            result.setIngredients(ingredients);
            result.setTags(tags);
            User user = recipe.getUser();
            result.setUsername(user.getUsername());
            result.setProfileImageUrl(user.getProfileImageUrl());
            resultList.add(result);
        }
        return new PageImpl<>(resultList, pageable, recipePage.getTotalElements());

    }

    @Transactional(readOnly = true)
    public List<SelectRecipeResponseDto> selectByTerm(String term) {
        List<Recipe> recipePage = recipeQueryDslRepository.findByKeyword(term);
        List<SelectRecipeResponseDto> resultList = new ArrayList<>();
        for (Recipe recipe : recipePage) {
            SelectRecipeResponseDto result = SelectRecipeResponseDto.fromEntity(recipe);
            List<IngredientResponseDto> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(recipe.getId());
            ingredients.forEach(ingredient -> {
                String displayName = ingredient.getUnit().getDisplayName();
                ingredient.setUnitDisplayName(displayName);
            });
            List<Tag> tags = tagRecipeQueryDslRepository.findTagsByRecipeId(recipe.getId());
            result.setIngredients(ingredients);
            result.setTags(tags);
            User user = recipe.getUser();
            result.setUsername(user.getUsername());
            result.setProfileImageUrl(user.getProfileImageUrl());
            resultList.add(result);
        }
        return resultList;
    }

}
