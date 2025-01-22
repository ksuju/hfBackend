package com.ll.hfback.domain.member.report.controller;

import com.ll.hfback.domain.member.report.dto.ReportRequest;
import com.ll.hfback.domain.member.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reports")
public class ApiV1ReportController {

    private final ReportService reportService;

    // MEM04_REPORT01 : 회원 신고하기
    @PostMapping
    public void createReport(@RequestBody ReportRequest request) {
        // reportService.createReport(request);
    }

    // MEM04_REPORT02 : 신고 누적 회원 차단 처리


    // MEM08_REPORT01 : 신고 목록 (관리자)
    @GetMapping
    public void getReports() {
        // reportService.getReports();
    }

    // MEM08_REPORT02 : 허위 신고 제거 (관리자)


    // MEM08_REPORT03 : 신고 확정 처리 (관리자)


}
