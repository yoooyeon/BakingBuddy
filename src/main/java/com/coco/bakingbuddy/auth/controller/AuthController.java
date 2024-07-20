package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.auth.service.AuthService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.jwt.JwtTokenProvider;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@RequiredArgsConstructor
@Slf4j
@Controller
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("signup")
    public String signup(Model model) {
        model.addAttribute("createUserRequestDto", new CreateUserRequestDto());
        return "user/signup";
    }
    @PostMapping("/refresh-token")
    public ResponseEntity<String> refreshToken(@RequestBody String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String newToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
            return ResponseEntity.ok(newToken);
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid refresh token");
        }
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
    @GetMapping("/test")
    public String test() {
        return "user/test";
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto user) {
        String accessToken = authService.getAccessToken(user);
        String refreshToken = authService.getRefreshToken(user);
        return ResponseEntity.ok(Map.of("accessToken", accessToken, "refreshToken", refreshToken));
    }

}
