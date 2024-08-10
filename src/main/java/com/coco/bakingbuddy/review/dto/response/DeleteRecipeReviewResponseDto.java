package com.coco.bakingbuddy.review.dto.response;

import com.coco.bakingbuddy.user.dto.response.SelectUserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class DeleteRecipeReviewResponseDto {
    private SelectUserResponseDto user;
    private String content;
    private int rating; // 1-5점 사이의 평점
}
