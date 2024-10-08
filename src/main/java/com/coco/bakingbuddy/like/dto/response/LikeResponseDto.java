package com.coco.bakingbuddy.like.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LikeResponseDto {
    private boolean userLiked;
    private Integer likeCount;
}
