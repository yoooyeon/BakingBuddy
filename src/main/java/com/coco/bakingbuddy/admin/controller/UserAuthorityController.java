package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.dto.response.SelectUserAuthorityRequestDto;
import com.coco.bakingbuddy.admin.service.UserAuthorityService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/authorities")
public class UserAuthorityController {
    private final UserAuthorityService userAuthorityService;

    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectUserAuthorityRequestDto>>>
    selectUserAuthorityRequests() {
        return toResponseEntity("권한 승인 요청 조회 성공"
                , userAuthorityService.selectUserAuthorityRequests());
    }

    @PostMapping("{id}")
    public ResponseEntity<SuccessResponse<String>>
    approveAuthority(@PathVariable("id") Long id, @AuthenticationPrincipal User user) {
        userAuthorityService.approveAuthority(id,user);
        return toResponseEntity("권한 요청 승인 성공");
    }
}
