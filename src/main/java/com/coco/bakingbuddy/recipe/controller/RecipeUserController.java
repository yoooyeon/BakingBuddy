package com.coco.bakingbuddy.recipe.controller;

import com.coco.bakingbuddy.ranking.service.RankingService;
import com.coco.bakingbuddy.recipe.service.DirectoryService;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.redis.service.RedisService;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
@Controller
public class RecipeUserController {
    private final RecipeService recipeService;
    private final UserService userService;

    @GetMapping("users/{userId}")
    public String selectRecipeByUserId(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/user-recipe";
    }
}
