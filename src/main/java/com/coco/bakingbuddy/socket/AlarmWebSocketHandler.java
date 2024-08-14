package com.coco.bakingbuddy.socket;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AlarmWebSocketHandler {

    private final SimpMessageSendingOperations messagingTemplate;

    public void sendAlarmToUser(String username, String msg) {

        // "/sub/alarm/{userId}" 경로로 메시지 전송
        messagingTemplate.convertAndSend("/sub/alarm/" + username, msg);
    }
}