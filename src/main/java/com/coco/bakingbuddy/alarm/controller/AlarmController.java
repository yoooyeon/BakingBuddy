package com.coco.bakingbuddy.alarm.controller;

import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.service.AlarmService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@Slf4j
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@RestController
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("users")
    public ResponseEntity<SuccessResponse<List<SelectAlarmResponseDto>>>
    selectAlarmByUserId(@AuthenticationPrincipal User user) {
        return toResponseEntity("유저 알람 조회 성공", alarmService.selectByUserId(user.getId()));
    }
}
