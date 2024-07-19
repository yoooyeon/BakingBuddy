package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.batch.event.UserRegistrationEvent;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
    @PostMapping("signup")
    public String register(@Valid @ModelAttribute CreateUserRequestDto user, Model model) {
        try {
            Long userId = userService.registerUser(user);
//            eventPublisher.publishEvent(new UserRegistrationEvent(this, userId));
            // TODO 클라이언트에게 WebSocket을 통해 알림 보내기
//            messagingTemplate.convertAndSendToUser(user.getUsername(),"/queue/user", "가입 축하");
            return "redirect:/login";

        } catch (CustomException ex) {
            model.addAttribute("errorCode", ex.getCode().getStatus().name());
            model.addAttribute("errorMessage", ex.getCode().getMessage());
            return "user/signup"; // 에러 발생 시 회원가입 페이지로 이동
        }
    }
    @GetMapping("/login")
    public String login() {
        return "user/login";
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
