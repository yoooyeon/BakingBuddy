package com.coco.bakingbuddy.auth.service;

import com.coco.bakingbuddy.auth.dto.request.LoginRequestDto;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.response.LoginUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import static com.coco.bakingbuddy.global.error.ErrorCode.INVALID_CREDENTIALS;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public String getAccessToken(LoginRequestDto loginRequest) {
        try {
            // id, pw 검증
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
            );
            return jwtTokenProvider.createAccessToken(authentication);
        } catch (AuthenticationException e) {
            throw new CustomException(INVALID_CREDENTIALS);
        }
    }

    public String getRefreshToken(String username) {
        return jwtTokenProvider.createRefreshToken(username);
    }

    public LoginUserResponseDto login(LoginRequestDto user, HttpServletResponse response) {
        String accessToken = getAccessToken(user);
        String refreshToken = getRefreshToken(user.getUsername());

        jwtTokenProvider.addTokenToCookie(response, accessToken, "accessToken");
        jwtTokenProvider.addTokenToCookie(response, refreshToken, "refreshToken");

        User saved = userRepository.findByUsername(user.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        LoginUserResponseDto result = LoginUserResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .roleType(saved.getRole().getDisplayName())
                .build();
        log.info(">>>>role={}",result.getRoleType());
        return result;

    }
}
