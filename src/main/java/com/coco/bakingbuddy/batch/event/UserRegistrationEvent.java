package com.coco.bakingbuddy.batch.event;

// 회원가입 시 발생하는 이벤트
public class UserRegistrationEvent {
    private final Long userId;

    public UserRegistrationEvent(Object source, Long userId) {
        super();
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }
}
