package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.auth.dto.request.LoginRequestDto;
import com.coco.bakingbuddy.auth.service.AuthService;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.global.response.ErrorResponse;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

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

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Map<String, String>>> login(@RequestBody LoginRequestDto user, HttpServletResponse response) {
        String accessToken = authService.getAccessToken(user);
        String refreshToken = authService.getRefreshToken(user);
        jwtTokenProvider.addTokenToCookie(response, accessToken, "accessToken");
        jwtTokenProvider.addTokenToCookie(response, refreshToken, "refreshToken");
        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        return toResponseEntity("토큰 생성", tokens);
    }

    @ResponseBody
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String newToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
            return toResponseEntity("리프레시 토큰으로 새 토큰 발급 성공",newToken);
        } else {
            return toResponseEntity(HttpStatus.FORBIDDEN, "리프레시 토큰 Invalid");
        }
    }

}
