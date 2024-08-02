package com.coco.bakingbuddy.socket.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
@Slf4j
@Controller
public class WebSocketController {
    private int onlineUsers = 0;

    @MessageMapping("/userConnected")
    @SendTo("/topic/onlineUsers")
    public int userConnected() {
        onlineUsers++;
        log.info(">>>userConnected={}",onlineUsers);
        return onlineUsers;
    }

    @MessageMapping("/userDisconnected")
    @SendTo("/topic/onlineUsers")
    public int userDisconnected() {
        onlineUsers--;
        log.info(">>>userDisconnected={}",onlineUsers);
        return onlineUsers;
    }
//    @MessageMapping("/send")
//    @SendTo("/topic/recipes")
//    public RecipeMessage sendMessage(RecipeMessage message) {
//        // Handle the incoming message
//        return message;
//    }
}
