package com.coco.bakingbuddy.admin.controller;

import com.coco.bakingbuddy.admin.dto.request.CreateRecipeReportRequestDto;
import com.coco.bakingbuddy.admin.dto.request.CreateReviewReportRequestDto;
import com.coco.bakingbuddy.admin.service.ReportService;
import com.coco.bakingbuddy.global.response.SuccessResponse;
import com.coco.bakingbuddy.user.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reports")
public class ReportController {
    private final ReportService reportService;

    /**
     * 리뷰 신고하기
     * @param dto
     * @param user
     * @return
     */
    @PostMapping("review")
    public ResponseEntity<SuccessResponse<String>>
    reviewReportRegister(@RequestBody CreateReviewReportRequestDto dto,
             @AuthenticationPrincipal User user) {
        reportService.reviewReportRegister(dto, user);
        return SuccessResponse.toResponseEntity("리뷰 신고 등록 성공");
    }

    /**
     * 레시피 신고하기
     * @param dto
     * @param user
     * @return
     */
    @PostMapping("recipe")
    public ResponseEntity<SuccessResponse<String>>
    recipeReportRegister(@RequestBody CreateRecipeReportRequestDto dto,
             @AuthenticationPrincipal User user) {
        reportService.recipeReportRegister(dto, user);
        return SuccessResponse.toResponseEntity("레시피 신고 등록 성공");
    }


}
