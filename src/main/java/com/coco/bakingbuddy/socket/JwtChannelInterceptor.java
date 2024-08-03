package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE + 99) // security보다 높은 우선순위
@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;
    private final ConnectedUserManager connectedUserManager;


    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor headerAccessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (headerAccessor == null) {
            return message;
        }

        // Extract token from cookies
        String token = extractTokenFromAuthorizationHeader(headerAccessor);
        // If the command is CONNECT, validate the token
        if (headerAccessor.getCommand() == StompCommand.CONNECT && token != null) {
            try {
                String username = jwtTokenProvider.getUserPk(token);
                connectedUserManager.addUser(username);
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        username, null, List.of(new SimpleGrantedAuthority("USER")));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                headerAccessor.addNativeHeader("username", username);
            } catch (Exception e) {
                log.error("Error while extracting token or validating user", e);
            }
        }

        return message;
    }

    private String extractTokenFromAuthorizationHeader(StompHeaderAccessor accessor) {
        List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String authHeader = authorizationHeaders.get(0); // Get the first Authorization header
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                return authHeader.substring(7).trim(); // Remove "Bearer " prefix and return the token
            }
        }
        return null;
    }


}
