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
public class SelectUserProfileResponseDto {
    private Long id;
    private String username;
    private String nickname;
    private String profileImageUrl;
    private String introduction;

    public static SelectUserProfileResponseDto fromEntity(User user) {
        return SelectUserProfileResponseDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .build();
    }
}
