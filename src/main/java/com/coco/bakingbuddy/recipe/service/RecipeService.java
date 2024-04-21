package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<SelectRecipeResponseDto> selectAll() {
        return recipeRepository.findAll().stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }

    public SelectRecipeResponseDto selectById(Long id) {
        return SelectRecipeResponseDto.fromEntity(recipeRepository.findById(id).orElseThrow());
    }

    public CreateRecipeResponseDto create(CreateRecipeRequestDto dto) {
        return CreateRecipeResponseDto.fromEntity(recipeRepository.save(CreateRecipeRequestDto.toEntity(dto)));
    }

    public DeleteRecipeResponseDto delete(DeleteRecipeRequestDto dto) {
        Long id = dto.getId();
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        recipe.delete();
        return DeleteRecipeResponseDto.fromEntity(recipe);
    }
}
