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
public class UserNicknameAndProfileDto {
    private Long id;
    private String nickname;
    private String profileImageUrl;

    public static UserNicknameAndProfileDto fromEntity(User user) {
        return UserNicknameAndProfileDto.builder()
                .nickname(user.getNickname())
                .profileImageUrl(user.getProfileImageUrl())
                .id(user.getId())
                .build();
    }
}
