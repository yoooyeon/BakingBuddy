package com.coco.bakingbuddy.alarm.service;

import com.coco.bakingbuddy.alarm.dto.response.SelectAlarmResponseDto;
import com.coco.bakingbuddy.alarm.repository.AlarmQueryDslRepository;
import com.coco.bakingbuddy.alarm.repository.AlarmRepository;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.recipe.dto.response.SelectRecipeResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmService {
    private final AlarmRepository alarmRepository;
    private final AlarmQueryDslRepository alarmQueryDslRepository;
    public List<SelectAlarmResponseDto> selectByUserId(Long userId) {
        return alarmQueryDslRepository.findByUserId(userId)
                .stream().map(SelectAlarmResponseDto::fromEntity).collect(Collectors.toList());

    }
}
