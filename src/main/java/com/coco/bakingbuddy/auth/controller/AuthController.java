package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.auth.dto.request.LoginRequestDto;
import com.coco.bakingbuddy.auth.service.AuthService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto.fromEntity;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("signup")
    public ResponseEntity<SuccessResponse<CreateUserResponseDto>> register(
            @Valid @RequestBody CreateUserRequestDto user) {
        return toResponseEntity("가입 성공", fromEntity(userService.registerUser(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<SuccessResponse<Map<String, String>>> login(@RequestBody LoginRequestDto user, HttpServletResponse response) {
        String accessToken = authService.getAccessToken(user);
        String refreshToken = authService.getRefreshToken(user);
        jwtTokenProvider.addTokenToCookie(response, accessToken, "accessToken");
        jwtTokenProvider.addTokenToCookie(response, refreshToken, "refreshToken");
        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        return toResponseEntity("로그인 성공, 토큰 생성", tokens);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
            String newToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
            return toResponseEntity("리프레시 토큰으로 새 토큰 발급 성공", newToken);
        } else {
            return toResponseEntity(HttpStatus.FORBIDDEN, "리프레시 토큰 Invalid");
        }
    }

}
