package com.coco.bakingbuddy.user.controller;

import com.coco.bakingbuddy.user.dto.request.CreateUserRequestDto;
import com.coco.bakingbuddy.user.dto.request.LoginUserRequestDto;
import com.coco.bakingbuddy.user.dto.response.CreateUserResponseDto;
import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import com.coco.bakingbuddy.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/api/users")
@RequiredArgsConstructor
@RestController
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<SelectUserResponseDto> allUsers() {
        return userService.selectAll();
    }

    @PostMapping("login")
    public boolean login(@RequestBody LoginUserRequestDto user) {

        return userService.authenticate(user);
    }

    @PostMapping("register") // 회원 등록 요청
    public CreateUserResponseDto register(@RequestBody CreateUserRequestDto user) {
        return userService.registerUser(user);
    }
}
