package com.coco.bakingbuddy.jwt;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RefreshToken {
    @Id
    private String token;
    private String username;
    private boolean valid;  // 토큰이 유효한지 여부
}
