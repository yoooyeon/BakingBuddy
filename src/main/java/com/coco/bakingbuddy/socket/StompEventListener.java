package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@Component
public class StompEventListener {

    private final Set<String> userSet = new ConcurrentSkipListSet<>();

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            log.info(">>>User connected: {}", username);
            log.info(">>>StompEventListener handleWebSocketConnectListener {}", username);
            userSet.add(username);

        } else {
            log.info(">>>StompEventListenerNo authentication found");
        }

    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            String username = user.getUsername();
            log.info(">>>User disconnected: {}", username);
            log.info(">>>StompEventListener handleWebSocketConnectListener {}", username);
            userSet.remove(username);
        } else {
            log.info(">>>StompEventListenerNo authentication found");
        }
    }


}
