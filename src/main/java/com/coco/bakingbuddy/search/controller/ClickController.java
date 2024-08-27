package com.coco.bakingbuddy.search.controller;

import com.coco.bakingbuddy.search.dto.request.CreateClickRequestDto;
import com.coco.bakingbuddy.search.service.ClickRecordService;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api/click")
@RequiredArgsConstructor
@RestController
public class ClickController {

    private final ClickRecordService clickRecordService;

    @PostMapping
    public void recordClick(@AuthenticationPrincipal User user,
                            @RequestBody CreateClickRequestDto dto) {
        clickRecordService.recordClick(user, dto);
    }
}
