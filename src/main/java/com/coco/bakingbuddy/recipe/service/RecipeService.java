package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.file.repository.RecipeStepRepository;
import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.domain.*;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.*;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;
import static com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto.fromEntity;

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
            result.setUsername(user.getUsername());
            result.setProfileImageUrl(user.getProfileImageUrl());
            resultList.add(result);
        }

        return new PageImpl<>(resultList, pageable, recipePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public SelectRecipeResponseDto selectById(Long id) {
        SelectRecipeResponseDto recipe = fromEntity(recipeQueryDslRepository.findById(id));
        List<Ingredient> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(id);
        List<Tag> tags = tagRecipeQueryDslRepository.findTagsByRecipeId(id);
        for (Tag tag : tags) {
            log.info(">>>tags={}", tag.getName());
        }
        recipe.setIngredients(ingredients);
        recipe.setTags(tags);
        User user = userRepository.findById(recipe.getUserId()).orElseThrow(()->new CustomException(USER_NOT_FOUND));
        recipe.setUsername(user.getUsername());
        recipe.setProfileImageUrl(user.getProfileImageUrl());
        return recipe;

    }

    @Transactional
//    public CreateRecipeResponseDto create(CreateRecipeRequestDto dto, MultipartFile multipartFile,MultipartFile[] stepImages) {
    public CreateRecipeResponseDto create(CreateRecipeRequestDto dto, MultipartFile multipartFile) {
        Directory directory = directoryRepository.findById(dto.getDirId())
                .orElseThrow(() -> new CustomException(ErrorCode.DIRECTORY_NOT_FOUND));
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        Recipe recipe = recipeRepository.save(CreateRecipeRequestDto.toEntity(dto));
        recipe.setDirectory(directory);
        recipe.setUser(user);
        List<RecipeStep> savedRecipeSteps = new ArrayList<>();
        for (RecipeStep recipeStep : dto.getRecipeSteps()) {
            RecipeStep save = recipeStepRepository.save(recipeStep);
            savedRecipeSteps.add(save);
        }
        recipe.setRecipeSteps(savedRecipeSteps);

        fileService.uploadRecipeImageFile(recipe.getId(), multipartFile);

        // 업로드된 단계별 이미지 파일 처리
        List<RecipeStep> recipeSteps = dto.getRecipeSteps();
        for (int i = 0; i < recipeSteps.size(); i++) {
            RecipeStep recipeStep = savedRecipeSteps.get(i);
//            MultipartFile stepImage = stepImages[i];
//            fileService.uploadRecipeStepImage(recipeStep.getId(), stepImage);
        }

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


    @Transactional(readOnly = true)
    public List<DirectoryWithRecipesResponseDto> selectDirsByUserId(Long userId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByUserId(userId);
        List<DirectoryWithRecipesResponseDto> result = new ArrayList<>();
        if (recipes == null || recipes.isEmpty()) {
            return null;
        }
        HashMap<Directory, List<Recipe>> recipeByDir = new HashMap<>();
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
            List<Recipe> list = recipeByDir.getOrDefault(recipe.getDirectory(), new ArrayList<>());
            list.add(recipe);
            recipeByDir.put(recipe.getDirectory(), list);
        }
        for (Directory key : recipeByDir.keySet()) {
            List<Recipe> recipe = recipeByDir.get(key);

            result.add(DirectoryWithRecipesResponseDto.builder()
                    .dirId(key.getId())
                    .dirName(key.getName())
                    .recipes(recipe.stream().map(RecipeResponseDto::fromEntity).collect(Collectors.toList()))
                    .build());

        }
        return result;
    }


}
