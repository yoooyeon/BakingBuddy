package com.coco.bakingbuddy.recipe.service;

import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.ingredient.domain.Ingredient;
import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.ingredient.domain.Unit;
import com.coco.bakingbuddy.ingredient.dto.request.CreateIngredientRequestDto;
import com.coco.bakingbuddy.ingredient.dto.response.IngredientResponseDto;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeQueryDslRepository;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeRepository;
import com.coco.bakingbuddy.ingredient.repository.IngredientRepository;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.DirectoryRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeRepository;
import com.coco.bakingbuddy.recipe.repository.RecipeStepRepository;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;
import static com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto.toEntity;

@Slf4j
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
    private final FileService fileService;
    private final RecipeStepRepository recipeStepRepository;

    @Transactional(readOnly = true)
    public List<SelectRecipeResponseDto> selectAll(@AuthenticationPrincipal User user) {
        List<Recipe> allRecipes = recipeRepository.findAll();
        Map<Long, List<IngredientResponseDto>> ingredientsMap = fetchIngredientsForRecipes(allRecipes);
        Map<Long, List<Tag>> tagsMap = fetchTagsForRecipes(allRecipes);

        return allRecipes.stream()
                .map(recipe -> buildSelectRecipeResponseDto(recipe, ingredientsMap, tagsMap,user))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Page<SelectRecipeResponseDto> selectAll(Pageable pageable) {
        Page<Recipe> recipePage = recipeQueryDslRepository.findAll(pageable);

        if (recipePage.isEmpty()) {
            return Page.empty();
        }

        Map<Long, List<IngredientResponseDto>> ingredientsMap = fetchIngredientsForRecipes(recipePage.getContent());
        Map<Long, List<Tag>> tagsMap = fetchTagsForRecipes(recipePage.getContent());

        List<SelectRecipeResponseDto> resultList = recipePage.getContent().stream()
                .map(recipe -> buildSelectRecipeResponseDto(recipe, ingredientsMap, tagsMap,null))
                .collect(Collectors.toList());

        return new PageImpl<>(resultList, pageable, recipePage.getTotalElements());
    }

    @Transactional(readOnly = true)
    public SelectRecipeResponseDto selectById(Long recipeId, User user) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new CustomException(RECIPE_NOT_FOUND));

        SelectRecipeResponseDto dto = SelectRecipeResponseDto.fromEntity(recipe);
        List<IngredientResponseDto> ingredients = ingredientRecipeQueryDslRepository.findIngredientsByRecipeId(recipeId);
        ingredients.forEach(ingredient -> {
            String displayName = ingredient.getUnit().getDisplayName();
            ingredient.setUnitDisplayName(displayName);
        });
        dto.setIngredients(ingredients);
        dto.setTags(tagRecipeQueryDslRepository.findTagsByRecipeId(recipeId));
        dto.setRecipeSteps(recipeStepRepository.findByRecipe(recipe).orElse(Collections.emptyList()));
        dto.setServings(recipe.getIngredientRecipes().get(0).getServings());
        dto = setUserDetails(dto, recipe,user);
        return dto;
    }

    @Transactional
    public CreateRecipeResponseDto create(Long userId, CreateRecipeRequestDto dto, MultipartFile multipartFile) {
        Directory directory = directoryRepository.findById(dto.getDirId()).orElseThrow(() -> new CustomException(DIRECTORY_NOT_FOUND));
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Recipe recipe = recipeRepository.save(toEntity(dto));
        recipe.setDirectory(directory);
        recipe.setUser(user);
        saveTags(dto.getTags(), recipe);
        saveIngredients(dto.getIngredients(), recipe, dto.getServings());
        fileService.uploadRecipeImageFile(recipe.getId(), multipartFile);

        return CreateRecipeResponseDto.fromEntity(recipe);
    }

    @Transactional
    public CreateRecipeResponseDto edit(EditRecipeRequestDto dto) {
        Recipe recipe = EditRecipeRequestDto.toEntity(dto);
        Recipe saved = recipeRepository.save(recipe);
        return CreateRecipeResponseDto.fromEntity(saved);
    }

    @Transactional
    public DeleteRecipeResponseDto delete(Long id, User user) {
        // 레시피 존재 여부 확인
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new CustomException(RECIPE_NOT_FOUND));

        log.info(">>>recipe.getUser().getId()={}", recipe.getUser().getId());
        log.info(">>>user.getId()={}", user.getId());
        // 레시피 소유자 확인
        if (recipe.getUser().getId() != user.getId()) {
            throw new CustomException(UNAUTHORIZED_DELETE); // 권한 없음 오류 처리
        }

        // 레시피 삭제 처리 (물리적 삭제 또는 논리적 삭제)
        recipeRepository.delete(recipe);

        // 삭제된 레시피를 DTO로 반환
        return DeleteRecipeResponseDto.fromEntity(recipe);
    }

    public List<SelectRecipeResponseDto> selectByDirectoryId(Long directoryId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByDirectoryId(directoryId);
        if (recipes == null || recipes.isEmpty()) {
            return Collections.emptyList();
        }
        return recipes.stream().map(SelectRecipeResponseDto::fromEntity).collect(Collectors.toList());
    }

    private Map<Long, List<IngredientResponseDto>> fetchIngredientsForRecipes(List<Recipe> recipes) {
        List<Long> recipeIds = recipes.stream().map(Recipe::getId).collect(Collectors.toList());
        return ingredientRecipeQueryDslRepository.findIngredientsByRecipeIds(recipeIds);
    }

    private Map<Long, List<Tag>> fetchTagsForRecipes(List<Recipe> recipes) {
        List<Long> recipeIds = recipes.stream().map(Recipe::getId).collect(Collectors.toList());
        return tagRecipeQueryDslRepository.findTagsByRecipeIds(recipeIds);
    }

    private SelectRecipeResponseDto buildSelectRecipeResponseDto
            (Recipe recipe, Map<Long, List<IngredientResponseDto>> ingredientsMap, Map<Long, List<Tag>> tagsMap, User user) {
        SelectRecipeResponseDto dto = SelectRecipeResponseDto.fromEntity(recipe);
        dto.setIngredients(ingredientsMap.get(recipe.getId()));
        dto.setTags(tagsMap.get(recipe.getId()));
        dto = setUserDetails(dto, recipe, user);
        return dto;
    }

    private SelectRecipeResponseDto setUserDetails(SelectRecipeResponseDto dto, Recipe recipe,User user) {
        boolean userLiked = recipe.getLikes().stream().anyMatch(like -> like.getUser().getId().equals(user.getId()));
        dto.setUserLiked(userLiked);
        dto.setUsername(user.getUsername());
        dto.setProfileImageUrl(user.getProfileImageUrl());
        return dto;
    }

    private void saveTags(List<String> tags, Recipe recipe) {
        if (tags != null) {
            for (String tagName : tags) {
                Tag tag = tagRepository.findByName(tagName).orElseGet(() -> tagRepository.save(Tag.builder().name(tagName).build()));
                TagRecipe tagRecipe = new TagRecipe();
                tagRecipe.addRecipe(recipe);
                tagRecipe.addTag(tag);
                tagRecipeRepository.save(tagRecipe);
            }
        }
    }

    private void saveIngredients(List<CreateIngredientRequestDto> ingredients, Recipe recipe, int servings) {
        if (ingredients != null && !ingredients.isEmpty()) {
            for (CreateIngredientRequestDto dto : ingredients) {
                Ingredient ingredient = ingredientRepository.findByName(dto.getName()).orElseGet(() ->
                        ingredientRepository.save(
                                Ingredient.builder()
                                        .name(dto.getName())
                                        .build()));
                ingredientRecipeRepository.save(IngredientRecipe.builder()
                        .amount(dto.getAmount())
                        .servings(servings)
                        .unit(Unit.from(dto.getUnitDisplayName()))
                        .ingredient(ingredient)
                        .recipe(recipe)
                        .build());
            }
        }
    }
}
