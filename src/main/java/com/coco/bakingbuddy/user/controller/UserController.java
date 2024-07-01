package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.request.LoginUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    @GetMapping
    public String allUsers(Model model) {
        List<SelectUserResponseDto> dto = userService.selectAll();
        model.addAttribute("users", dto);
        return "user/user-list";
    }

    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("createUserRequestDto", new CreateUserRequestDto());
        return "user/signup";
    }

    @PostMapping("signup")
    public String register(@ModelAttribute CreateUserRequestDto user) {
        Long userId = userService.registerUser(user);
        return "redirect:/api/users/login?userId=" + userId;
    }

    @GetMapping("login")
    public String login(@RequestParam("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/user";
    }

    @PostMapping("login")
    public boolean login(@RequestBody LoginUserRequestDto user) {
        return userService.authenticate(user);
    }


}
