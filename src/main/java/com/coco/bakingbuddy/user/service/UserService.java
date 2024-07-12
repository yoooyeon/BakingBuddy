package com.coco.bakingbuddy.user.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long registerUser(CreateUserRequestDto user) {
        if (isDuplicated(user.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

//        RoleType role = RoleType.ROLE_USER;
        return userRepository.save(User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .role("USER") // todo admin과 어떻게 나눌지
                .build()).getId();
    }

    private boolean isDuplicated(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }

//    public LoginUserResponseDto authenticate(LoginUserRequestDto user) {
//        Optional<User> registered = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//        if (registered.isPresent()) {
//            return LoginUserResponseDto.builder()
//                    .success(true)
//                    .userId(registered.get().getId())
//                    .build();
//        }
//        return LoginUserResponseDto.builder()
//                .success(false)
//                .userId(null)
//                .build();
//    }

    @Transactional(readOnly = true)
    public List<SelectUserResponseDto> selectAll() {
        return userRepository.findAll().stream().map(SelectUserResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User selectById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }

    public Optional<User> findByUsernameAndPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }
    @Transactional(readOnly = true)

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

    }
}
