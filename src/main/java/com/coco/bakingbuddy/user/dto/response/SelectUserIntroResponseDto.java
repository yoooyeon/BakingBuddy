package com.coco.bakingbuddy.user.dto.response;

import com.coco.bakingbuddy.recipe.dto.response.DirectoryWithRecipesResponseDto;
import com.coco.bakingbuddy.user.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectUserIntroResponseDto {
    private String username;
    private String nickname;
    private String profileImageUrl;
    private String introduction;
    private List<DirectoryWithRecipesResponseDto> dirs;


    public static SelectUserIntroResponseDto fromEntity(User user) {
        return SelectUserIntroResponseDto.builder()
                .username(user.getUsername())
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .introduction(user.getIntroduction())
                .build();
    }
}
