package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.dto.request.CreateRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.DeleteRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.request.EditRecipeRequestDto;
import com.coco.bakingbuddy.recipe.dto.response.CreateRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.DeleteRecipeResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectDirectoryResponseDto;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeController {
    private final RecipeService recipeService;
    private final UserService userService;
    private final DirectoryService directoryService;
    private final RedisService redisService;
    private final RankingService rankingService;

    @GetMapping("search")
    public String search(@AuthenticationPrincipal User user,
                         Model model,
                         @NotNull(message = "검색어를 한 글자 이상 입력해주세요.") @RequestParam(name = "term", required = false) String term,
                         @RequestParam(name = "level", required = false) String level,
                         @RequestParam(name = "time", required = false) Integer time,
                         @RequestParam(name = "page", defaultValue = "0") int page,
                         @RequestParam(name = "size", defaultValue = "7") int size) {
        // 키워드, 필터 조건을 기반으로 검색한 결과를 페이징하여 가져옴
//        Page<Recipe> recipePage = recipeService.findRecipes(keyword, difficulty, time, PageRequest.of(page, size));
//        log.info("user=" + user.toString());
        if (user != null) {
            model.addAttribute("user", user);
        }
        Page<SelectRecipeResponseDto> recipePage;
        if (term != null && !term.isEmpty()) {
            recipePage = recipeService.selectByTerm(term, PageRequest.of(page, size));
            redisService.saveSearchTerm(term);
            rankingService.incrementSearchCount(term); // ranking counter
        } else {
            recipePage = recipeService.selectAll(PageRequest.of(page, size));
        }
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
    }

    @GetMapping
    public String selectAll(@AuthenticationPrincipal User user,
                            Model model,
                            @RequestParam(name = "page", defaultValue = "0") int page,
                            @RequestParam(name = "size", defaultValue = "7") int size) {
        if (user != null) {
            model.addAttribute("user", user);
        }
        Page<SelectRecipeResponseDto> recipePage = null;
        recipePage = recipeService.selectAll(PageRequest.of(page, size));
        model.addAttribute("recipes", recipePage.getContent());
        model.addAttribute("currentPage", recipePage.getNumber());
        model.addAttribute("totalPages", recipePage.getTotalPages());
        return "recipe/recipe-list";
    }

    @ResponseBody
    @GetMapping("directories/{directoryId}")
    public List<SelectRecipeResponseDto> selectByDirectoryId(@PathVariable("directoryId") Long directoryId) {
        return recipeService.selectByDirectoryId(directoryId);
    }

    @GetMapping("{id}")
    public String selectById(@PathVariable("id") Long id, Model model) {
        SelectRecipeResponseDto dto = recipeService.selectById(id);
        model.addAttribute("recipe", dto);
        return "recipe/recipe";
    }

    @GetMapping("register")
    public String register(Model model, @RequestParam("userId") Long userId) {
        List<SelectDirectoryResponseDto> dirs = directoryService.selectByUserId(userId);
        model.addAttribute("userId", userId);
        model.addAttribute("dirs", dirs);
        return "recipe/register-recipe";
    }

    @ResponseBody
    @PostMapping
    public CreateRecipeResponseDto create(@Valid @RequestPart("dto") CreateRecipeRequestDto dto,
                                          @RequestParam("recipeImage") MultipartFile recipeImage) {
        CreateRecipeResponseDto save = recipeService.create(dto, recipeImage);
        return save;
    }

    @ResponseBody
    @DeleteMapping
    public DeleteRecipeResponseDto create(@Valid @RequestBody DeleteRecipeRequestDto dto) {
        return recipeService.delete(dto);
    }

    @GetMapping("{recipeId}/edit") // 레시피 수정 화면 조회
    public String editRecipe(@PathVariable("recipeId") Long recipeId, Model model) {
        SelectRecipeResponseDto recipe = recipeService.selectById(recipeId);
        model.addAttribute("recipe", recipe);
        return "recipe/edit-recipe";
    }

    @ResponseBody
    @PutMapping("{recipeId}/edit")
    public CreateRecipeResponseDto editRecipe(@PathVariable("recipeId") Long recipeId, @Valid @RequestBody EditRecipeRequestDto dto) {
        dto.setId(recipeId);
        return recipeService.edit(dto);
    }

    @GetMapping("users/{userId}")
    public String selectRecipeByUserId(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/user-recipe";
    }
}
