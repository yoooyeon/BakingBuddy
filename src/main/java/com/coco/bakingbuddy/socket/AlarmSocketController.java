package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.alarm.dto.request.AlarmRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@RequiredArgsConstructor
public class AlarmSocketController {
    private final SimpMessageSendingOperations template;

    @MessageMapping("/alarm")
    public void followAlarm(@Payload AlarmRequest alarmRequest, SimpMessageHeaderAccessor headerAccessor) {
        template.convertAndSend("/sub/alarm/" + alarmRequest.getId(), alarmRequest);
    }

    @MessageMapping("/recipe")
    public void newRecipe(SimpMessageHeaderAccessor headerAccessor) {
        template.convertAndSend("/sub/recipe/follow");
    }
}
