package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AuthController {
    private final UserService userService;
    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("createUserRequestDto", new CreateUserRequestDto());
        return "user/signup";
    }
    @GetMapping("login")
    public String login() {
        return "user/login";
    }

//    @PostMapping("login")
//    public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto dto) {
//        LoginResponseDto response = userService.validateUserForLogin(dto);
//        log.info("response={}",response);
//        if (response.isSuccess()) {
//            return ResponseEntity.ok(response);
//        } else {
//            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
//        }
//    }
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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) {
        log.info(">>>> LOGOUT");
        SecurityContextLogoutHandler securityContextLogoutHandler = new SecurityContextLogoutHandler();
        securityContextLogoutHandler.logout(request, response,
                SecurityContextHolder.getContext().getAuthentication());
        model.addAttribute("user", null);
        model.addAttribute("loggedIn", false);
        return "recipe/recipe-list";
    }


}
