package com.coco.bakingbuddy.user.dto.response;

import com.coco.bakingbuddy.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectUserResponseDto {
    private Long id;
    private String username;
    private String nickname;

    public static SelectUserResponseDto fromEntity(User user) {
        return SelectUserResponseDto.builder()
                .username(user.getUsername())
                .nickname(user.getNickname())
                .id(user.getId())
                .build();
    }
}
