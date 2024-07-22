package com.coco.bakingbuddy.alarm.controller;

import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@RequestMapping("/api/alarms")
@RequiredArgsConstructor
@Controller
public class AlarmController {
    private final AlarmService alarmService;

    @GetMapping("users/{userId}")
    public String selectByUserId(@PathVariable("userId") Long userId, Model model) {
        List<SelectAlarmResponseDto> alarms = alarmService.selectByUserId(userId);
        model.addAttribute("alarms", alarms);
        return "alarm/user-alarm-list";
    }
    @GetMapping("/socket")
    public String socket() {
        return "socket-test";
    }
}
