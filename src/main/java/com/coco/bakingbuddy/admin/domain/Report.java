package com.coco.bakingbuddy.admin.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Report extends BaseTime {
    @Column(name = "REPORT_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTER_ID")
    private User reporter; // 신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "REPORTED_ID")
    private User reported; // 신고 대상자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType reportType;

    @Column(nullable = false)
    private String reason;

    @Builder.Default
    @Column(nullable = false)
    private boolean isCompleted = false;
    private User completeAdmin;

    // 신고 완료처리
    public void complete(User admin) {
        completeAdmin = admin;
        isCompleted = true;
    }
}
