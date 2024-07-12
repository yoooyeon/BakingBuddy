package com.coco.bakingbuddy.user.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.coco.bakingbuddy.user.domain.User;
import com.coco.bakingbuddy.user.domain.User;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateUserResponseDto {
    private Long id;
    private String username;

    public static CreateUserResponseDto fromEntity(User user) {
        return CreateUserResponseDto.builder()
                .username(user.getUsername())
                .id(user.getId())
                .build();
    }
}
