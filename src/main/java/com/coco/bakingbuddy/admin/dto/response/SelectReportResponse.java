package com.coco.bakingbuddy.admin.dto.response;

import com.coco.bakingbuddy.admin.domain.Report;
import com.coco.bakingbuddy.admin.domain.ReportType;
import com.coco.bakingbuddy.user.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class SelectReportResponse {
    private Long id;
    private String reporter; // 신고자
    private String reported; // 신고 대상자
    private String reportType;
    private String reason;

    private boolean isCompleted;
    private String completeAdmin;

    public static SelectReportResponse fromEntity(Report report) {
        return SelectReportResponse.builder()
                .id(report.getId())
                .reporter(report.getReporter().getUsername())
                .reported(report.getReported().getUsername())
                .completeAdmin(report.getCompleteAdmin() != null ? report.getCompleteAdmin().getUsername() : "Not assigned")
                .reason(report.getReason())
                .reportType(report.getReportType().getDisplayName())
                .build();
    }
}
