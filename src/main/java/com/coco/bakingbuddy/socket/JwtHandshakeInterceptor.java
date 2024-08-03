package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Slf4j
@RequiredArgsConstructor
public class JwtHandshakeInterceptor extends HttpSessionHandshakeInterceptor {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
        String jwtToken = extractJwtTokenFromCookies(request);
        if (jwtToken != null) {
            String username = jwtTokenProvider.getUserPk(jwtToken);
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    username, "", List.of(new SimpleGrantedAuthority("USER")));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            // wsHandler
            // StompHeaderAccessor
            log.info(">>>JwtHandshakeInterceptor 토큰 유효 userPk={}", username);
            // 사용자 정보를 attributes에 저장
            attributes.put("username", username);
        } else {
            log.info(">>>토큰 없음");
        }
        return super.beforeHandshake(request, response, wsHandler, attributes);
    }

    private String extractJwtTokenFromCookies(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        List<String> cookies = headers.get(HttpHeaders.COOKIE);

        if (cookies != null) {
            for (String cookie : cookies) {
                // 쿠키 문자열을 ';'로 분리하여 각각의 쿠키를 파싱합니다.
                String[] cookieArray = cookie.split(";");
                for (String c : cookieArray) {
                    // 각 쿠키 문자열을 '='로 분리하여 키와 값을 얻습니다.
                    String[] keyValue = c.split("=");
                    if (keyValue.length == 2) {
                        String key = keyValue[0].trim();
                        String value = keyValue[1].trim();

                        // 'accessToken' 쿠키의 값을 반환합니다.
                        if ("accessToken".equalsIgnoreCase(key)) {
                            return value;
                        }
                    }
                }
            }
        }
        return null;
    }

}
