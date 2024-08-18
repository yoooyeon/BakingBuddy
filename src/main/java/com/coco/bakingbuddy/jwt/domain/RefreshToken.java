package com.coco.bakingbuddy.jwt.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    private String token;
    @Column(unique = true)
    private String username;
    private boolean valid;  // 토큰이 유효한지 여부
}
