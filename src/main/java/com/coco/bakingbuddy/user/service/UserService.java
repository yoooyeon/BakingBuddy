package com.coco.bakingbuddy.user.service;

import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.request.LoginUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.LoginUserResponseDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;


    @Transactional
    public Long registerUser(CreateUserRequestDto user) {
        if (isDuplicated(user.getUsername())) {
            throw new CustomException(ErrorCode.DUPLICATE_USERNAME);
        }

        User save = userRepository.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .build());

        return save.getId();
    }

    private boolean isDuplicated(String username) {
        if (userRepository.findByUsername(username).isPresent()) {
            return true;
        }
        return false;
    }

    public LoginUserResponseDto authenticate(LoginUserRequestDto user) {
        Optional<User> registered = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (registered.isPresent()) {
            return LoginUserResponseDto.builder()
                    .success(true)
                    .userId(registered.get().getId())
                    .build();
        }
        return LoginUserResponseDto.builder()
                .success(false)
                .userId(null)
                .build();
    }

    @Transactional(readOnly = true)
    public List<SelectUserResponseDto> selectAll() {
        return userRepository.findAll().stream().map(SelectUserResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public User selectById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
    }


}
