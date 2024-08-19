package com.coco.bakingbuddy.admin.domain;

import com.coco.bakingbuddy.global.domain.BaseTime;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.service.RoleType;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserAuthority extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_AUTHORITY_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private User user; // 승인 요청한 사용자

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoleType roleType; // 승인 요청 역할/권한

    @Column(nullable = false)
    private boolean approval; // 승인 여부

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "APPROVAL_USER_ID")
    private User approvalUser; // 승인한 관리자

    public void updateApprovalUser(User user) {
        this.approvalUser = user;
    }
}
