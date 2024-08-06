package com.coco.bakingbuddy.user.service;

import com.coco.bakingbuddy.file.service.FileService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.ingredient.domain.Ingredient;
import com.coco.bakingbuddy.ingredient.domain.IngredientRecipe;
import com.coco.bakingbuddy.ingredient.dto.response.IngredientResponseDto;
import com.coco.bakingbuddy.ingredient.repository.IngredientRecipeQueryDslRepository;
import com.coco.bakingbuddy.recipe.domain.Directory;
import com.coco.bakingbuddy.recipe.domain.Recipe;
import com.coco.bakingbuddy.recipe.dto.response.DirectoryWithRecipesResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.RecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.repository.RecipeQueryDslRepository;
import com.coco.bakingbuddy.search.dto.response.RecentSearchResponseDto;
import com.coco.bakingbuddy.tag.domain.Tag;
import com.coco.bakingbuddy.tag.domain.TagRecipe;
import com.coco.bakingbuddy.tag.repository.TagRecipeQueryDslRepository;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserIntroResponseDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.*;
import static com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto.fromEntity;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final FileService fileService;
    private final RecipeQueryDslRepository recipeQueryDslRepository;
    private final TagRecipeQueryDslRepository tagRecipeQueryDslRepository;
    private final IngredientRecipeQueryDslRepository ingredientRecipeQueryDslRepository;

    @Transactional
    public User registerUser(CreateUserRequestDto user) {
        if (isDuplicated(user.getUsername())) throw new CustomException(DUPLICATE_USERNAME); // 아이디 중복 체크
//        log.info(">>>user.getRole {}", user.getRole());
        return userRepository.save(User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .nickname(user.getNickname())
                .role(RoleType.from(user.getRole()))
                .uuid(UUID.randomUUID())
                .build());
    }

    private boolean isDuplicated(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<SelectUserResponseDto> selectAll() {
        return userRepository.findAll().stream()
                .map(SelectUserResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User selectById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
    }

    public List<RecentSearchResponseDto> findRecentSearchesByUserId(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        return user.getRecentSearches().stream()
                .map(RecentSearchResponseDto::fromEntity)
                .sorted((dto1, dto2) -> dto2.getTimestamp().compareTo(dto1.getTimestamp()))
                .limit(10)
                .collect(Collectors.toList());
    }

    public User editUserInfo(Long userId, String username, String nickname, String introduction, MultipartFile profileImage) {
        User user = userRepository.findById(userId).orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        user.updateNickname(nickname);
        user.updateUsername(username);
        user.updateIntroduction(introduction);
        if (profileImage != null && !profileImage.isEmpty()) {
            String url = fileService.uploadUserProfileImageFile(userId, profileImage);
            user.updateProfile(url);
        }
        return userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<DirectoryWithRecipesResponseDto> selectRecipesGroupbyDirByUserId(Long userId) {
        List<Recipe> recipes = recipeQueryDslRepository.findByUserId(userId);
        List<DirectoryWithRecipesResponseDto> result = new ArrayList<>();
        if (recipes == null || recipes.isEmpty()) {
            return new ArrayList<>();
        }
        HashMap<Directory, List<Recipe>> recipeByDir = new HashMap<>();
        List<Tag> tags = new ArrayList<>();
        List<IngredientResponseDto> ingredients = new ArrayList<>();
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
                ingredients.add(IngredientResponseDto.builder()
                        .name(ingredient.getName())
                        .amount(ingredientRecipe.getAmount())
                        .unit(ingredientRecipe.getUnit())
                        .build());
            }
            SelectRecipeResponseDto selectRecipeResponseDto = fromEntity(recipe);
            selectRecipeResponseDto.setIngredients(ingredients);
            selectRecipeResponseDto.setTags(tags);
            selectRecipeResponseDto.setServings(ingredientRecipes.get(0).getServings());
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

    public SelectUserIntroResponseDto selectIntroByUuid(UUID uuid) {
        User user = userRepository.findByUuid(uuid).orElseThrow(() -> new CustomException(UUID_NOT_FOUND));
        SelectUserIntroResponseDto dto = SelectUserIntroResponseDto.fromEntity(user);
        dto.setDirs(selectRecipesGroupbyDirByUserId(user.getId()));
        return dto;

    }
}
