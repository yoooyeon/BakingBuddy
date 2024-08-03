package com.coco.bakingbuddy.socket.controller;

import com.coco.bakingbuddy.socket.ConnectedUserManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@RequiredArgsConstructor
@Slf4j
@Controller
public class WebSocketController {
    private final ConnectedUserManager connectedUserManager;

    @MessageMapping("/userConnected")
    @SendTo("/topic/onlineUsers")
    public int userConnected() {
        return connectedUserManager.getConnectedCount();
    }

    @MessageMapping("/userDisconnected")
    @SendTo("/topic/onlineUsers")
    public int userDisconnected() {
        return connectedUserManager.getConnectedCount();
    }

    @EventListener
    public void handleSessionDisconnect(SessionDisconnectEvent event) {
//        String simpSessionId = (String) event.getMessage().getHeaders().get("accessToken");
        String username = event.getUser().getName();// You might need to adapt this based on how you identify users
        connectedUserManager.removeUser(username);
    }
}
