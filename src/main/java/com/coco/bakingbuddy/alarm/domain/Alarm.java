package com.coco.bakingbuddy.alarm.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Alarm extends BaseTime {
    @Column(name = "ALARM_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String msg;
    private String type; // 알림 종류
    @Builder.Default
    private boolean isRead = false; // 읽음 여부

    public void markAsRead() {
        this.isRead = true;
    }
}
