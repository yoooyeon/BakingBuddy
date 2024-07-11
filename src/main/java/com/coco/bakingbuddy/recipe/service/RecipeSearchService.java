package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeSearchService {
    private final RecipeQueryDslRepository recipeQueryDslRepository;

    @Transactional(readOnly = true)
    public Page<SelectRecipeResponseDto> selectByTerm(String keyword, Pageable pageable) {
        Page<Recipe> recipePage = recipeQueryDslRepository.findByKeyword(keyword, pageable);
        if (recipePage.isEmpty()) {
            return Page.empty(); // 빈 페이지 객체를 반환
        }
        return recipePage.map(SelectRecipeResponseDto::fromEntity); // Recipe를 SelectRecipeResponseDto로 매핑하여 반환
    }

}
