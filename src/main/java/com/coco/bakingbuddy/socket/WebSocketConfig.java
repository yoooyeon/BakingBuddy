package com.coco.bakingbuddy.socket;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        //stomp의 접속 주소
        registry.addEndpoint("/ws").setAllowedOriginPatterns("*").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        //클라이언트의 send요청 처리
        registry.setApplicationDestinationPrefixes("/pub");
        //sub하는 클라이언트에게 메시지 전달
        registry.enableSimpleBroker("/sub");

    }}
