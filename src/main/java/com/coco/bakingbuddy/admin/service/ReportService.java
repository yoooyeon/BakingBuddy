package com.coco.bakingbuddy.admin.service;

import com.coco.bakingbuddy.admin.domain.Report;
import com.coco.bakingbuddy.admin.dto.request.CreateRecipeReportRequestDto;
import com.coco.bakingbuddy.admin.dto.request.CreateReviewReportRequestDto;
import com.coco.bakingbuddy.admin.dto.response.SelectReportResponse;
import com.coco.bakingbuddy.admin.repository.ReportRepository;
import com.coco.bakingbuddy.alarm.service.AlarmService;
import com.coco.bakingbuddy.global.error.ErrorCode;
import com.coco.bakingbuddy.global.error.exception.CustomException;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ReportService {
    private final ReportRepository reportRepository;
    private final AlarmService alarmService;

    @Transactional(readOnly = true)
    public List<SelectReportResponse> selectAll() {
        return reportRepository.findAll().stream().map(SelectReportResponse::fromEntity).collect(Collectors.toList());
    }

    @Transactional
    public void complete(Long id, User admin) {
        Report report = reportRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCode.REPORT_NOT_FOUND));
        report.complete(admin);
        alarmService.createAlarm(report.getReporter().getId(), "신고 조치가 완료되었습니다.");
        alarmService.createAlarm(report.getReported().getId(), report.getReason() + "로 신고되었습니다.");
    }

    @Transactional
    public void recipeReportRegister(CreateRecipeReportRequestDto dto, User user) {

    }

    @Transactional
    public void reviewReportRegister(CreateReviewReportRequestDto dto, User user) {

    }
}
