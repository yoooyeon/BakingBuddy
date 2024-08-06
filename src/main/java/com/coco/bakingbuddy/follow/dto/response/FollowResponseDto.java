package com.coco.bakingbuddy.follow.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class FollowResponseDto {
    private String username;
    private UUID uuid;
    private String profileImageUrl;

}
