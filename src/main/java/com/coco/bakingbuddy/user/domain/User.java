package com.coco.bakingbuddy.user.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.util.ArrayList;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // userId
//    private Long userId;
    private String nickname;
    // 관심 태그
//    private List<Tag> tags = new ArrayList<>();
    //
    private boolean alarmYn; // 알림 설정
}
