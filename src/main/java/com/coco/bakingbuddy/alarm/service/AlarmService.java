package com.coco.bakingbuddy.alarm.service;
import com.coco.bakingbuddy.user.domain.User;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.repository.AlarmQueryDslRepository;
import com.coco.bakingbuddy.alarm.repository.AlarmRepository;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final UserRepository userRepository;
    private final AlarmQueryDslRepository alarmQueryDslRepository;

    @Transactional(readOnly = true)
    public List<SelectAlarmResponseDto> selectByUserId(Long userId) {
        return alarmQueryDslRepository.findByUserId(userId)
                .stream().map(SelectAlarmResponseDto::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public void updateAlarm(Long userId, String msg) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        alarmRepository.save(Alarm.builder()
                .msg(msg)
                .readYn("N")
                .user(user)
                .build());
    }
}
