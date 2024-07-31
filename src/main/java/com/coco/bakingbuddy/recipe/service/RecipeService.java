package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.ingredient.domain.Ingredient;
import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeQueryDslRepository;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeRepository;
import com.coco.bakingbuddy.ingredient.repository.IngredientRepository;
import com.coco.bakingbuddy.recipe.domain.*;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class RecipeService {
    private final String RECIPE_UPLOAD_PATH = "RecipeProfile/";
    private final String BUCKET_NAME = "baking-buddy-bucket";
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
    private final FileService fileService;
    private final RecipeStepRepository recipeStepRepository;

    @Transactional(readOnly = true)
    public List<SelectRecipeResponseDto> selectAll() {
        List<Recipe> all = recipeRepository.findAll();

        List<Long> recipeIds = all.stream()
                .map(Recipe::getId)
                .collect(Collectors.toList());

        Map<Long, List<Ingredient>> ingredientsMap = ingredientRecipeQueryDslRepository.findIngredientsByRecipeIds(recipeIds);
        Map<Long, List<Tag>> tagsMap = tagRecipeQueryDslRepository.findTagsByRecipeIds(recipeIds);

        List<SelectRecipeResponseDto> resultList = new ArrayList<>();
        for (Recipe recipe : all) {
            SelectRecipeResponseDto result = SelectRecipeResponseDto.fromEntity(recipe);
            result.setIngredients(ingredientsMap.get(recipe.getId()));
            result.setTags(tagsMap.get(recipe.getId()));
            User user = recipe.getUser();
            boolean userLiked = recipe.getLikes().stream()
                    .anyMatch(like -> like.getUser().equals(user));
            result.setUserLiked(userLiked);
            result.setUsername(user.getUsername());
            result.setProfileImageUrl(user.getProfileImageUrl());
            resultList.add(result);
        }

        return resultList;
    }

    @Transactional(readOnly = true)
    public Page<SelectRecipeResponseDto> selectAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeQueryDslRepository.findAll(pageable);
        if (recipePage.isEmpty()) {
            return Page.empty();
        }

        List<Long> recipeIds = recipePage.getContent().stream()
                .map(Recipe::getId)
                .collect(Collectors.toList());

        Map<Long, List<Ingredient>> ingredientsMap = ingredientRecipeQueryDslRepository.findIngredientsByRecipeIds(recipeIds);
        Map<Long, List<Tag>> tagsMap = tagRecipeQueryDslRepository.findTagsByRecipeIds(recipeIds);

        List<SelectRecipeResponseDto> resultList = new ArrayList<>();
        for (Recipe recipe : recipePage) {
            SelectRecipeResponseDto result = SelectRecipeResponseDto.fromEntity(recipe);
            result.setIngredients(ingredientsMap.get(recipe.getId()));
            result.setTags(tagsMap.get(recipe.getId()));
            User user = recipe.getUser();
            boolean userLiked = recipe.getLikes().stream()
                    .anyMatch(like -> like.getUser().equals(user));
            result.setUserLiked(userLiked);
            result.setUsername(user.getUsername());
            result.setProfileImageUrl(user.getProfileImageUrl());
            resultList.add(result);
        }

        return new PageImpl<>(resultList, pageable, recipePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public SelectRecipeResponseDto selectById(Long recipeId) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new CustomException(RECIPE_NOT_FOUND));
        SelectRecipeResponseDto result = SelectRecipeResponseDto.fromEntity(recipe);

        List<Ingredient> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(recipeId);
        List<Tag> tags = tagRecipeQueryDslRepository.findTagsByRecipeId(recipeId);
        Optional<List<RecipeStep>> recipeSteps = recipeStepRepository.findByRecipe(recipe);
        if (recipeSteps.isPresent()) {
            result.setRecipeSteps(recipeSteps.get());
        }
        result.setIngredients(ingredients);
        result.setTags(tags);
        User user = recipe.getUser();
        boolean userLiked = recipe.getLikes().stream()
                .anyMatch(like -> like.getUser().equals(user));
        result.setUserLiked(userLiked);
        result.setUsername(user.getUsername());
        result.setProfileImageUrl(user.getProfileImageUrl());
        return result;
    }

    @Transactional
    public CreateRecipeResponseDto create(Long userId, CreateRecipeRequestDto dto, MultipartFile multipartFile) {
        Directory directory = directoryRepository.findById(dto.getDirId())
                .orElseThrow(() -> new CustomException(DIRECTORY_NOT_FOUND));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Recipe recipe = recipeRepository.save(CreateRecipeRequestDto.toEntity(dto));
        recipe.setDirectory(directory);
        recipe.setUser(user);
//        List<RecipeStep> savedRecipeSteps = new ArrayList<>();
//        if (!dto.getRecipeSteps().isEmpty() && dto.getRecipeSteps().size() > 0) {
//            for (RecipeStep recipeStep : dto.getRecipeSteps()) {
//                RecipeStep save = recipeStepRepository.save(recipeStep);
//                savedRecipeSteps.add(save);
//            }
//            recipe.setRecipeSteps(savedRecipeSteps);
//        }
        fileService.uploadRecipeImageFile(recipe.getId(), multipartFile);

        List<String> tags = dto.getTags();
        Tag tag = null;
        if (tags != null) {
            for (String tagName : tags) {
                TagRecipe tagRecipe = new TagRecipe();
                if (tagRepository.findByName(tagName).isPresent()) {
                    tag = tagRepository.findByName(tagName).get();
                } else {
                    tag = tagRepository.save(Tag.builder().name(tagName).build());
                }
                tagRecipe.addRecipe(recipe);
                tagRecipe.addTag(tag);
                tagRecipeRepository.save(tagRecipe);
            }
        }

        List<String> ingredients = dto.getIngredients();
        Ingredient ingredient = null;
        if (ingredients != null && ingredients.size() > 0) {
            for (String name : ingredients) {
                IngredientRecipe ingredientRecipe = new IngredientRecipe();
                if (ingredientRepository.findByName(name).isPresent()) {
                    ingredient = ingredientRepository.findByName(name).get();
                    ingredientRecipe.addRecipe(recipe);
                    ingredientRecipe.addIngredient(ingredient);
                    ingredientRecipeRepository.save(ingredientRecipe);
                } else {
                    ingredient = ingredientRepository.save(Ingredient.builder().name(name).build());
                    ingredientRecipe.addRecipe(recipe);
                    ingredientRecipe.addIngredient(ingredient);
                    ingredientRecipeRepository.save(ingredientRecipe);
                }
            }
        }
        return CreateRecipeResponseDto.fromEntity(recipe);
    }

    @Transactional
    public CreateRecipeResponseDto edit(EditRecipeRequestDto dto) {
        Recipe recipe = EditRecipeRequestDto.toEntity(dto);
        Recipe saved = recipeRepository.save(recipe);
        return CreateRecipeResponseDto.fromEntity(saved);
    }

    @Transactional
    public DeleteRecipeResponseDto delete(DeleteRecipeRequestDto dto) {
        Long id = dto.getId();
        Recipe recipe = recipeRepository.findById(id).orElseThrow();
        recipe.delete();
        return DeleteRecipeResponseDto.fromEntity(recipe);
    }

    public List<SelectRecipeResponseDto> selectByDirectoryId(Long directoryId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByDirectoryId(directoryId);
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }


}
