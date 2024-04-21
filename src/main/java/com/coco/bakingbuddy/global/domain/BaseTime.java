package com.coco.bakingbuddy.global.domain;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseTime {

    @Column(updatable = false)
    @CreatedDate
    private LocalDateTime createdDate;
    @Column
    @LastModifiedDate
    private LocalDateTime modifiedDate;

    public void setCurrentTime(LocalDateTime time) {
        this.createdDate = time;
        this.modifiedDate = time;
    }
}