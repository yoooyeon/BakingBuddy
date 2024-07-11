package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
public class AuthController {
//    @GetMapping("/")
//    public String index(@AuthenticationPrincipal User user, Model model) {
//        boolean loggedIn = user != null;
//        model.addAttribute("loggedIn", loggedIn);
//        return "recipe/recipe-list"; // index.html 템플릿
//    }

    @GetMapping("login") // 화면 이동
    public String login() {
        return "user/login";
    }

    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("createUserRequestDto", new CreateUserRequestDto());
        return "user/signup";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        new SecurityContextLogoutHandler().logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        return "redirect:/login";
    }

}
