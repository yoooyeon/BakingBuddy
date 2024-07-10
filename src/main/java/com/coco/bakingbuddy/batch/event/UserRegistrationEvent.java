package com.coco.bakingbuddy.batch.event;

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
