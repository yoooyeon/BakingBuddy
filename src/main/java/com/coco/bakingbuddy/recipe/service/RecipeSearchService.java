package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.IngredientRecipeQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.repository.TagRecipeQueryDslRepository;
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
            List<Ingredient> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(recipe.getId());
            List<Tag> tags = tagRecipeQueryDslRepository.findTagsByRecipeId(recipe.getId());
            result.setIngredients(ingredients);
            result.setTags(tags);
            resultList.add(result);
            for (Ingredient ingredient : ingredients) {
                log.info(">>>ingredient name{}", ingredient.getName());
            }
        }
        return new PageImpl<>(resultList, pageable, recipePage.getTotalElements());

    }

}
