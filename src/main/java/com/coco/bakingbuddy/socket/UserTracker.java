package com.coco.bakingbuddy.socket;

import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.jwt.provider.JwtTokenProvider;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static com.coco.bakingbuddy.global.error.ErrorCode.USERNAME_NOT_FOUND;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserTracker extends TextWebSocketHandler {
    private final ConcurrentMap<Long, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final MessagingService messagingService;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String token = extractToken(session);
        Long userId = verifyTokenAndGetUserId(token);
        log.info(">>>>afterConnectionEstablished token={}", token);
        log.info(">>>>afterConnectionEstablished userId={}", userId);

        if (userId != null) {
            sessions.put(userId, session);
            log.info(">>>>afterConnectionEstablished 성공={}", userId);
            broadcastUserCount();
        } else {
            session.close(CloseStatus.NOT_ACCEPTABLE.withReason("Invalid token"));
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        String token = extractToken(session);
        Long userId = verifyTokenAndGetUserId(token);
        if (userId != null) {
            sessions.remove(userId);
            broadcastUserCount();
        }
    }

    private void broadcastUserCount() {
        messagingService.broadcastUserCount(sessions.size());
    }

    private String extractToken(WebSocketSession session) {
        String token = session.getHandshakeHeaders().getFirst("Cookie");
        if (token != null) {
            for (String cookie : token.split(";")) {
                String[] parts = cookie.split("=");
                if (parts[0].trim().equals("accessToken")) {
                    return parts[1].trim();
                }
            }
        }
        return null;
    }

    private Long verifyTokenAndGetUserId(String token) {
        String userPk = jwtTokenProvider.getUserPk(token);
        User user = userRepository.findByUsername(userPk)
                .orElseThrow(() -> new CustomException(USERNAME_NOT_FOUND));
        return user.getId();
    }
}
