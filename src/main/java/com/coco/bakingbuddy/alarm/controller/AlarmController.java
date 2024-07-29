package com.coco.bakingbuddy.alarm.controller;

import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.service.AlarmService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("users/{userId}")
    public ResponseEntity<SuccessResponse<List<SelectAlarmResponseDto>>>
    selectByUserId(@PathVariable("userId") Long userId) {
        return toResponseEntity("유저 알람 조회 성공", alarmService.selectByUserId(userId));
    }
}
