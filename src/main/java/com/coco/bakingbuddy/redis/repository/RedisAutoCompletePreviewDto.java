package com.coco.bakingbuddy.redis.repository;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;

@RedisHash("term")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
public class RedisAutoCompletePreviewDto {
    @Id
    private String name;
    private Long recipeId;
    private String imageUrl;

}
