package com.coco.bakingbuddy.socket.controller;

import com.coco.bakingbuddy.socket.ConnectedUserManager;
import com.coco.bakingbuddy.socket.JwtChannelInterceptor;
import com.coco.bakingbuddy.socket.StompEventListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Slf4j
@Controller
public class WebSocketController {
    private final ConnectedUserManager connectedUserManager;
    @MessageMapping("/userConnected")
    @SendTo("/topic/onlineUsers")
    public int userConnected() {
        int onlineUsers = connectedUserManager.getConnectedCount();
        log.info(">>>userConnected={}",onlineUsers);
        return onlineUsers;
    }

    @MessageMapping("/userDisconnected")
    @SendTo("/topic/onlineUsers")
    public int userDisconnected() {
        int onlineUsers = connectedUserManager.getConnectedCount();
        log.info(">>>userDisconnected={}",onlineUsers);
        return onlineUsers;
    }

}
