package com.coco.bakingbuddy.socket.config;

import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.socket.ConnectedUserManager;
import com.coco.bakingbuddy.socket.JwtChannelInterceptor;
import com.coco.bakingbuddy.socket.JwtHandshakeInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@RequiredArgsConstructor
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //    private final JwtChannelInterceptor jwtChannelInterceptor;
    private final JwtTokenProvider jwtTokenProvider;
    private final ConnectedUserManager connectedUserManager;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // SOP
                .addInterceptors(new JwtHandshakeInterceptor(jwtTokenProvider))
                .withSockJS();
    }


    @Override
    public void configureClientInboundChannel(ChannelRegistration registration) {
        registration.interceptors(new JwtChannelInterceptor(jwtTokenProvider, connectedUserManager));
    }

//    @Bean
//    public MyWebSocketHandler myWebSocketHandler() {
//        return new UserCounterHandler();
//    }
}
