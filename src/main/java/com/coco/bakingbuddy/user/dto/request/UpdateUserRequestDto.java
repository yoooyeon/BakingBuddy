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
public class UpdateUserRequestDto {
    @NotBlank(message = "username 이 빈 값일 수 없습니다.")
    private String username;
//    @NotBlank(message = "nickname 이 빈 값일 수 없습니다.")
    private String nickname;
    private String profileImage;
    @NotBlank(message = "userId 이 빈 값일 수 없습니다.")
    private Long userId;

}
