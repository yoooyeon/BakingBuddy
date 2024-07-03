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
public class CreateUserRequestDto {
    @NotBlank(message = "username 이 빈 값일 수 없습니다.")
    private String username;
    @NotBlank(message = "password 이 빈 값일 수 없습니다.")
    private String password;
    @NotBlank(message = "nickname 이 빈 값일 수 없습니다.")
    private String nickname;
    private String profileImageUrl;

}
