package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.like.dto.response.LikeResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MessagingService {
    private final SimpMessagingTemplate messagingTemplate;

    public void broadcastUserCount(int userCount) {
        messagingTemplate.convertAndSend("/topic/user-count", userCount);
    }

    public void broadcastLikeUpdate(LikeResponseDto response) {
        messagingTemplate.convertAndSend("/topic/recipes", response);
    }
}
