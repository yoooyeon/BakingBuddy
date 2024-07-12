package com.coco.bakingbuddy.user.dto.request;

import com.coco.bakingbuddy.global.validator.ValidPassword;
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
    @ValidPassword(message = "비밀번호는 최소 8자 이상이며, 숫자, 대문자, 소문자, 특수 문자를 포함해야 합니다.")
    private String password;
    @NotBlank(message = "nickname 이 빈 값일 수 없습니다.")
    private String nickname;
    private String profileImageUrl;

}
