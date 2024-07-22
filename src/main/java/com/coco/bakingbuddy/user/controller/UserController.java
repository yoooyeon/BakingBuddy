package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.recipe.service.RecipeService;
import com.coco.bakingbuddy.user.dto.request.UpdateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Controller
public class UserController {
    private final UserService userService;
    private final RecipeService recipeService;

    private final ApplicationEventPublisher eventPublisher;

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

    @GetMapping("{userId}/edit-user")
    public String editUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.selectById(userId));
        model.addAttribute("updateUserRequestDto", new UpdateUserRequestDto());
        return "user/edit-user";
    }


}
