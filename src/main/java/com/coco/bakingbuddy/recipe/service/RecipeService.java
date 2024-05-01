package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import com.coco.bakingbuddy.recipe.repository.IngredientRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.tag.repository.TagRecipeRepository;
import com.coco.bakingbuddy.tag.repository.TagRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto.fromEntity;

@RequiredArgsConstructor
@Service
public class RecipeService {
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final DirectoryRepository directoryRepository;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private final TagRecipeRepository tagRecipeRepository;
    private final RecipeQueryDslRepository recipeQueryDslRepository;

    @Transactional(readOnly = true)

    public List<SelectRecipeResponseDto> selectAll() {
        return recipeRepository.findAll().stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)

    public SelectRecipeResponseDto selectById(Long id) {
        return fromEntity(recipeQueryDslRepository.findById(id));

    }

    @Transactional
    public CreateRecipeResponseDto create(CreateRecipeRequestDto dto) {
        Directory directory = directoryRepository.findById(dto.getDirId()).orElseThrow();
        User user = userRepository.findById(dto.getUserId()).orElseThrow();
        Recipe recipe = recipeRepository.save(CreateRecipeRequestDto.toEntity(dto));
        recipe.setDirectory(directory);
        recipe.setUser(user);
        List<String> tags = dto.getTags();
        Tag tag = null;
        for (String tagName : tags) {
            if (tagRepository.findByName(tagName).isPresent()) {
                tag = tagRepository.findByName(tagName).get();
            } else {
                tag = tagRepository.save(Tag.builder().name(tagName).build());
            }
            TagRecipe tagRecipe = new TagRecipe();
            tagRecipe.addRecipe(recipe);
            tagRecipe.addTag(tag);
            tagRecipeRepository.save(tagRecipe);
        }
        List<String> ingredients = dto.getIngredients();
        for (String name : ingredients) {
            Ingredient ingredient = Ingredient.builder().name(name).recipe(recipe).build();
            ingredientRepository.save(ingredient);
        }
        return CreateRecipeResponseDto.fromEntity(recipe);
    }

    @Transactional
    public DeleteRecipeResponseDto delete(DeleteRecipeRequestDto dto) {
        Long id = dto.getId();
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        recipe.delete();
        return DeleteRecipeResponseDto.fromEntity(recipe);
    }

    @Transactional(readOnly = true)

    public List<SelectRecipeResponseDto> selectByUserId(Long userId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByUserId(userId);
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }

    public List<SelectRecipeResponseDto> selectByDirectoryId(Long directoryId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByDirectoryId(directoryId);
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }
}
