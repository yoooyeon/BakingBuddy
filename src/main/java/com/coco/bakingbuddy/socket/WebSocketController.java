package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.alarm.service.AlarmService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class WebSocketController {
    private final SimpMessagingTemplate messagingTemplate;
    private final AlarmService alarmService;

    @MessageMapping("/test") // prefix인 /send + /test
    @SendTo("/queue/test") // 다시 클라이언트 /topic/test로 발송 - subscribe
    public String handleTestMessage(String message, SimpMessageHeaderAccessor headerAccessor) {
        return message;
    }
}
