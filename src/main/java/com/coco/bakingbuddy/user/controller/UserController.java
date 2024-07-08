package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.request.UpdateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
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

    @GetMapping("{userId}")
    public String selectByUserId(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("dirs", recipeService.selectDirsByUserId(userId));
        return "user/my-page";
    }

    @GetMapping("{userId}/edit-user") //화면 이동
    public String editUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("updateUserRequestDto", new UpdateUserRequestDto());
        return "user/edit-user";
    }


    @PostMapping("signup")
    public String register(@Valid @ModelAttribute CreateUserRequestDto user, Model model) {
        try {
            Long userId = userService.registerUser(user);
            return "redirect:/login";
        } catch (CustomException ex) {
            model.addAttribute("errorCode", ex.getCode().getStatus().name());
            model.addAttribute("errorMessage", ex.getCode().getMessage());
            return "user/signup"; // 에러 발생 시 회원가입 페이지로 이동
        }
    }

}
