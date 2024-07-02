package com.coco.bakingbuddy.user.service;

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


    //    public CreateUserResponseDto registerUser(CreateUserRequestDto user) {
//        return CreateUserResponseDto.fromEntity(userRepository.save(User.builder()
//                .username(user.getUsername())
//                .password(user.getPassword())
//                .nickname(user.getNickname())
//                .build()));
//    }
    @Transactional

    public Long registerUser(CreateUserRequestDto user) {
        User save = userRepository.save(User.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .nickname(user.getNickname())
                .build());
        return save.getId();
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
        return userRepository.findById(userId).orElseThrow();
    }
}
