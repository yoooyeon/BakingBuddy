package com.coco.bakingbuddy.alarm.service;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.repository.AlarmQueryDslRepository;
import com.coco.bakingbuddy.alarm.repository.AlarmRepository;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.socket.AlarmWebSocketHandler;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static com.coco.bakingbuddy.global.error.ErrorCode.USER_NOT_FOUND;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final AlarmQueryDslRepository alarmQueryDslRepository;
    private final AlarmWebSocketHandler alarmWebSocketHandler;

    @Transactional(readOnly = true)
    public List<SelectAlarmResponseDto> selectByUserId(Long userId) {
        return alarmQueryDslRepository.findByUserId(userId)
                .stream().map(SelectAlarmResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public void updateAlarm(Long userId, String msg) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));
        alarmRepository.save(Alarm.builder()
                .msg(msg)
                .isRead(false)
                .user(user)
                .build());
    }

    @Transactional
    public void createAlarm(Long userId, String msg) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(USER_NOT_FOUND));

        Alarm alarm = Alarm.builder()
                .user(user)
                .msg(msg)
                .build();

        alarmRepository.save(alarm);
        sendAlarmToUser(user.getUsername(), msg);
    }

    public void sendAlarmToUser(String username, String message) {
        alarmWebSocketHandler.sendAlarmToUser(username, message);
    }

    public void updateReadStatus(Long id, User user) {
        Alarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.ALARM_NOT_FOUND));
        if (user.getId() != alarm.getUser().getId()) {
            throw new CustomException(ErrorCode.UNAUTHORIZED_READ);
        }
        alarm.markAsRead();
        alarmRepository.save(alarm); // 명시

    }
}
