package com.coco.bakingbuddy.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // userId
//    private Long userId;
    private String nickname;
    private String username;
    private String password;
    // 관심 태그
//    private List<Tag> tags = new ArrayList<>();
    //
    private boolean alarmYn; // 알림 설정


    public static User register(String username, String password) {
        // 사용자 객체 생성
        User user = User.builder()
                .username(username)
                .password(password)
                .build();
        // 사용자 등록 로직 추가 가능 (예: 이메일 확인, 초기 설정 등)
        return user;
    }
}
