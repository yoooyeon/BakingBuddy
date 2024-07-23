package com.coco.bakingbuddy.batch.listener;

import com.coco.bakingbuddy.alarm.service.AlarmService;
import com.coco.bakingbuddy.batch.event.UserRegistrationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserRegistrationListener {
    private final AlarmService alarmService;

    @EventListener
    public void handleUserRegistration(UserRegistrationEvent event) throws IOException {
        Long userId = event.getUserId();
        log.info("회원가입 축하합니다. 알림 발송. 사용자 ID: " + userId);
        String msg = "회원가입 축하합니다.";
        alarmService.updateAlarm(userId, msg);

    }
}
