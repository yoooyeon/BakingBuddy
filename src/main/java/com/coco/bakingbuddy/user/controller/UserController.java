package com.coco.bakingbuddy.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    @GetMapping
    public String index(Model model) {
        // 로그인 상태 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");

        // 로그인한 경우 유저 아이디
        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn) {
            model.addAttribute("username", authentication.getName());
        }
        return "index";
    }
    @PostMapping
    public String logIn(Model model) {
        // 로그인 상태 확인
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean loggedIn = authentication.isAuthenticated() && !authentication.getName().equals("anonymousUser");

        // 로그인한 경우 유저 아이디
        model.addAttribute("loggedIn", loggedIn);
        if (loggedIn) {
            model.addAttribute("username", authentication.getName());
        }
        return "index";
    }
}
