package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.dto.response.SelectReportResponse;
import com.coco.bakingbuddy.admin.service.ReportService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.coco.bakingbuddy.global.response.SuccessResponse.toResponseEntity;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/reports")
public class ReportAdminController {
    private final ReportService reportService;

    /**
     * 모든 신고 조회
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<SuccessResponse<List<SelectReportResponse>>> select() {
        return toResponseEntity("모든 신고 조회 성공", reportService.selectAll());
    }

    /**
     * 신고 완료 처리
     *
     * @param id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{id}")
    public void complete(@PathVariable("id") Long id,
                         @AuthenticationPrincipal User user) {
        reportService.complete(id, user);
    }


}
