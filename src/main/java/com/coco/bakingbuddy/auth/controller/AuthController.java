package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AuthController {
    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("createUserRequestDto", new CreateUserRequestDto());
        return "user/signup";
    }
    @GetMapping("test")
    public String test(Model model) {
        model.addAttribute("user", null);
        model.addAttribute("loggedIn", false);
        return "index";
    }
    @GetMapping("autocomplete")
    public String autocomplete(Model model) {
        return "recipe/autocomplete";
    }

//    @GetMapping("/logout")
//    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
//        log.info(">>>> LOGOUT");
//        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
//        securityContextLogoutHandler.logout(request, response,
//                SecurityContextHolder.getContext().getAuthentication());
//        model.addAttribute("user", null);
//        model.addAttribute("loggedIn", false);
//        return "recipe/recipe-list";
//    }


}
