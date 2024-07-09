package com.coco.bakingbuddy.alarm.dto.response;

import com.coco.bakingbuddy.alarm.domain.Alarm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectAlarmResponseDto {
    private Long id;
    private String msg;
    private String type; // 알림 종류

    public static SelectAlarmResponseDto fromEntity(Alarm alarm) {
        return SelectAlarmResponseDto.builder()
                .id(alarm.getId())
                .msg(alarm.getMsg())
                .type(alarm.getType())
                .build();
    }
}
