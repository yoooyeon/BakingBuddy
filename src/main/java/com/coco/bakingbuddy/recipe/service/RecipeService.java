package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Ingredient;
import com.coco.bakingbuddy.recipe.domain.IngredientRecipe;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.*;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.tag.repository.TagRecipeQueryDslRepository;
import com.coco.bakingbuddy.tag.repository.TagRecipeRepository;
import com.coco.bakingbuddy.tag.repository.TagRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
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
    private final TagRecipeQueryDslRepository tagRecipeQueryDslRepository;
    private final IngredientRecipeQueryDslRepository ingredientRecipeQueryDslRepository;
    private final IngredientRecipeRepository ingredientRecipeRepository;
    private final RecipeQueryDslRepository recipeQueryDslRepository;

    @Transactional(readOnly = true)

    public List<SelectRecipeResponseDto> selectAll() {
        List<Recipe> recipes = recipeQueryDslRepository.findAll();
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
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
        TagRecipe tagRecipe = new TagRecipe();

        for (String tagName : tags) {
            if (tagRepository.findByName(tagName).isPresent()) {
                tag = tagRepository.findByName(tagName).get();
            } else {
                tag = tagRepository.save(Tag.builder().name(tagName).build());
            }
            tagRecipe.addRecipe(recipe);
            tagRecipe.addTag(tag);
            tagRecipeRepository.save(tagRecipe);
        }
        List<String> ingredients = dto.getIngredients();
        Ingredient ingredient = null;
        IngredientRecipe ingredientRecipe = new IngredientRecipe();
        for (String name : ingredients) {
            if (ingredientRepository.findByName(name).isPresent()) {
                ingredient = ingredientRepository.findByName(name).get();
            }else {
                ingredient = ingredientRepository.save(Ingredient.builder().name(name).build());
            }
            ingredientRecipe.addRecipe(recipe);
            ingredientRecipe.addIngredient(ingredient);
            ingredientRecipeRepository.save(ingredientRecipe);
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
        List<SelectRecipeResponseDto> result = new ArrayList<>();
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        List<Tag> tags = new ArrayList<>();
        List<Ingredient> ingredients = new ArrayList<>();
        for (Recipe recipe : recipes) {
            Long id = recipe.getId();
            List<TagRecipe> tagRecipes = tagRecipeQueryDslRepository.findByRecipeId(id);
            List<IngredientRecipe> ingredientRecipes = ingredientRecipeQueryDslRepository.findByRecipeId(id);
            for (TagRecipe tagRecipe : tagRecipes) {
                Tag tag = tagRecipe.getTag();
                tags.add(tag);
            }
            for (IngredientRecipe ingredientRecipe : ingredientRecipes) {
                Ingredient ingredient = ingredientRecipe.getIngredient();
                ingredients.add(ingredient);
            }
            SelectRecipeResponseDto selectRecipeResponseDto = fromEntity(recipe);
            selectRecipeResponseDto.setIngredients(ingredients);
            selectRecipeResponseDto.setTags(tags);
            result.add(selectRecipeResponseDto);
        }
        return result;
    }

    public List<SelectRecipeResponseDto> selectByDirectoryId(Long directoryId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByDirectoryId(directoryId);
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }
}
