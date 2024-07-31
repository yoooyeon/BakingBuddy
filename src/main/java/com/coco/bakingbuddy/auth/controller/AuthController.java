package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.auth.dto.request.LoginRequestDto;
import com.coco.bakingbuddy.auth.service.AuthService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto.fromEntity;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@Slf4j
@RestController
public class AuthController {
    private final UserService userService;
    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @GetMapping("/api/auth/status")
    public ResponseEntity<?> status(
            @AuthenticationPrincipal User user) {
        if (user != null) {
            return toResponseEntity("로그인 상태 확인", Map.of("isAuthenticated", true));
        }
        return toResponseEntity(UNAUTHORIZED, "로그인 상태 X");
    }

    @PostMapping("signup")
    public ResponseEntity<SuccessResponse<CreateUserResponseDto>> register(
            @Valid @RequestBody CreateUserRequestDto user) {
        return toResponseEntity("가입 성공", fromEntity(userService.registerUser(user)));
    }

    @PostMapping("login")
    public ResponseEntity<SuccessResponse<Map<String, String>>> login(@RequestBody LoginRequestDto user, HttpServletResponse response) {
        // Generate access and refresh tokens
        String accessToken = authService.getAccessToken(user);
        String refreshToken = authService.getRefreshToken(user);

        // Store tokens in cookies
        jwtTokenProvider.addTokenToCookie(response, accessToken, "accessToken");
        jwtTokenProvider.addTokenToCookie(response, refreshToken, "refreshToken");

        // Create a response map with tokens (if you need to send them in the response body as well)
        Map<String, String> tokens = Map.of("accessToken", accessToken, "refreshToken", refreshToken);

        // Return a success response
        return toResponseEntity("로그인 성공, 토큰 생성", tokens);
    }


    //    @PostMapping("refresh-token")
//    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken) {
//        if (jwtTokenProvider.validateRefreshToken(refreshToken)) {
//            String newToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
//            return toResponseEntity("리프레시 토큰으로 새 토큰 발급 성공", newToken);
//        } else {
//            return toResponseEntity(HttpStatus.FORBIDDEN, "리프레시 토큰 Invalid");
//        }
//    }
    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");

        if (refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            return toResponseEntity(UNAUTHORIZED, "유효하지 않은 리프레시 토큰");
        }

        String newAccessToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
        return toResponseEntity("리프레시 토큰으로 새 엑세스 토큰 발급"
                , Map.of("accessToken", newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<SuccessResponse<String>> logout(HttpServletRequest request, HttpServletResponse response) {
        // JWT 쿠키 삭제
        Cookie accessTokenCookie = new Cookie("accessToken", null);
        accessTokenCookie.setPath("/");
        accessTokenCookie.setHttpOnly(true);
        accessTokenCookie.setMaxAge(0);
//        accessTokenCookie.setSecure(true); // HTTPS에서만 쿠키 전송

        Cookie refreshTokenCookie = new Cookie("refreshToken", null);
        refreshTokenCookie.setPath("/");
        refreshTokenCookie.setHttpOnly(true);
        refreshTokenCookie.setMaxAge(0);
//        refreshTokenCookie.setSecure(true); // HTTPS에서만 쿠키 전송

        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);

        return SuccessResponse.toResponseEntity("로그아웃 성공");
    }


}
