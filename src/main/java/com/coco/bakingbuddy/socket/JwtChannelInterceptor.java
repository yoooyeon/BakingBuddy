package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class JwtChannelInterceptor implements ChannelInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String token = extractTokenFromCookies(accessor);
            if (token != null && !token.isEmpty()) {
                try {
                    String username = jwtTokenProvider.getUserPk(token);
                    UsernamePasswordAuthenticationToken authentication =
                            new UsernamePasswordAuthenticationToken(username, null, List.of(new SimpleGrantedAuthority("USER")));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } catch (JwtException e) {
                    log.error(">>>JWT Verification Failed: " + e.getMessage());
                    return null; // 인증 실패 시 메시지 전송 중지
                }
            } else {
                log.error(">>>JWT token is not found in cookies");
                return null; // 쿠키에서 토큰이 없으면 메시지 전송 중지
            }
        }
        return message;
    }

    private String extractTokenFromCookies(StompHeaderAccessor accessor) {
        List<String> cookies = accessor.getNativeHeader("Cookie");
        if (cookies != null) {
            for (String cookie : cookies) {
                String[] cookiePairs = cookie.split(";");
                for (String cookiePair : cookiePairs) {
                    String[] keyValue = cookiePair.split("=");
                    if (keyValue.length == 2 && "accessToken".equalsIgnoreCase(keyValue[0].trim())) {
                        return keyValue[1].trim();
                    }
                }
            }
        }
        return null;
    }
}
