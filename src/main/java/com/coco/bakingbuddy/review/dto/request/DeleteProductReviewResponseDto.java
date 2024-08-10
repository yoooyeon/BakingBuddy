package com.coco.bakingbuddy.review.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeleteProductReviewResponseDto {
    private String content;

    private int rating; // 1-5점 사이의 평점
}
