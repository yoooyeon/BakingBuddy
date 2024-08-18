package com.coco.bakingbuddy.auth.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class AuthStatusResponseDto {
    private boolean isAuthenticated;
    private String roleType;
}
