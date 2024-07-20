package com.coco.bakingbuddy.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginUserRequestDto {
    @NotBlank(message = "username 이 빈 값일 수 없습니다.")
    private String username;
    @NotBlank(message = "password 이 빈 값일 수 없습니다.")
    private String password;

}