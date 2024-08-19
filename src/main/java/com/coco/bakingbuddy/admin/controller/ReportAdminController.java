package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.service.ReportService;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
    public void select() {
        reportService.selectAll();
    }

    /**
     * 신고 완료 처리
     * @param id
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("{id}")
    public void complete(@PathVariable("id") Long id,
                         @AuthenticationPrincipal User user) {
        reportService.complete(id,user);
    }


}
