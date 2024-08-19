package com.coco.bakingbuddy.admin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CreateReviewReportRequestDto {
    private Long reviewId;
    private String reason;
    private String reportType;
}
