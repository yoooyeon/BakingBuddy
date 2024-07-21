package com.coco.bakingbuddy.auth.service;

import com.coco.bakingbuddy.auth.controller.LoginRequestDto;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.jwt.JwtTokenProvider;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Slf4j
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

//    public String getToken(LoginRequestDto dto) {
//        User user = userRepository.findByUsername(dto.getUsername())
//                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
//
//        String token = jwtTokenProvider.createToken(user.getUsername(), user.getRole());
//        log.info(">>>token={}", token);
//        return token;
//    }

    public String getRefreshToken(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String token = jwtTokenProvider.createRefreshToken(user.getUsername());
        return token;
    }

    public String getAccessToken(LoginRequestDto dto) {
        User user = userRepository.findByUsername(dto.getUsername())
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        String token = jwtTokenProvider.createAccessToken(user.getUsername(), user.getRole());
        return token;
    }
}
