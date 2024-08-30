package com.coco.bakingbuddy.auth.controller;

import com.coco.bakingbuddy.auth.dto.request.LoginRequestDto;
import com.coco.bakingbuddy.auth.dto.response.AuthStatusResponseDto;
import com.coco.bakingbuddy.auth.service.AuthService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto;
import com.coco.bakingbuddy.user.dto.response.LoginUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static com.coco.bakingbuddy.global.response.ErrorResponse.toResponseEntity;
import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;
import static com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto.fromEntity;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
//@CrossOrigin(origins = "https://baking-buddy-frontend-h5cn.vercel.app")
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
            AuthStatusResponseDto auth = AuthStatusResponseDto.builder()
                    .roleType(user.getRole().getDisplayName()).isAuthenticated(true)
                    .build();
            return toResponseEntity("로그인 상태 확인", auth);
        }
        return toResponseEntity(UNAUTHORIZED, "로그인 상태 X");
    }

    @PostMapping("api/signup")
    public ResponseEntity<SuccessResponse<CreateUserResponseDto>> register(
            @Valid @RequestBody CreateUserRequestDto user) {
        return toResponseEntity("가입 성공", fromEntity(userService.registerUser(user)));
    }

    @PostMapping("api/login")
    public ResponseEntity<SuccessResponse<LoginUserResponseDto>>
    login(@RequestBody LoginRequestDto user, HttpServletResponse response) {
        return toResponseEntity("로그인 성공, 토큰 생성", authService.login(user,response));
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
    @PostMapping("/api/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody Map<String, String> body) {
        String accessToken = body.get("accessToken");

        if (accessToken == null || !jwtTokenProvider.validateAccessToken(accessToken)) {
            return toResponseEntity(UNAUTHORIZED, "유효하지 않은 리프레시 토큰, 재로그인 요청");
        }

        return toResponseEntity("엑세스 토큰 유효"
                , Map.of("valid", true));
    }

    @PostMapping("/api/refresh-token")
    public ResponseEntity<?> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String refreshToken = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refreshToken")) {
                    refreshToken = cookie.getValue();
                }
            }
        }
        log.info(">>>refreshToken={}", refreshToken);
        if (refreshToken == null || !jwtTokenProvider.validateRefreshToken(refreshToken)) {
            log.info(">>>refreshToken 유효하지않음");
            return toResponseEntity(UNAUTHORIZED, "유효하지 않은 리프레시 토큰, 재로그인 요청");
        }

        String newAccessToken = jwtTokenProvider.createAccessTokenFromRefreshToken(refreshToken);
        log.info(">>>newAccessToken{}", newAccessToken);
        // Store tokens in cookies
        jwtTokenProvider.addTokenToCookie(response, newAccessToken, "accessToken");
        return toResponseEntity("리프레시 토큰으로 새 엑세스 토큰 발급"
                , Map.of("accessToken", newAccessToken));
    }

    @PostMapping("api/logout")
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

        return toResponseEntity("로그아웃 성공");
    }


}
