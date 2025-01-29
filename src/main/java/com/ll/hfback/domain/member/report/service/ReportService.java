package com.ll.hfback.domain.member.report.service;

import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.domain.member.report.entity.Report;
import com.ll.hfback.domain.member.report.entity.Report.ReportState;
import com.ll.hfback.domain.member.report.repository.ReportRepository;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {

    private final ReportRepository reportRepository;
    private final MemberService memberService;


    @Transactional
    public Report createReport(Long reporterId, Long reportedId, String content) {
        Member reporter = memberService.findById(reporterId)
            .orElseThrow(() -> new ServiceException("createReport()", "신고자를 찾을 수 없습니다."));

        Member reported = memberService.findById(reportedId)
            .orElseThrow(() -> new ServiceException("createReport()", "신고 대상자를 찾을 수 없습니다."));

        if (reporter.equals(reported)) {
            throw new ServiceException("createReport()", "자기 자신을 신고할 수 없습니다.");
        }

        Report report = Report.builder()
            .reporter(reporter)
            .reported(reported)
            .content(content)
            .state(ReportState.WAITING)
            .build();

        reporter.addReport(report);
        return reportRepository.save(report);
    }


    public Page<Report> getReports(Pageable pageable) {
        return reportRepository.findAllWithMembers(pageable);
    }


    @Transactional
    public void rejectReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ServiceException("rejectReport()", "해당 신고를 찾을 수 없습니다."));

        report.reject();
    }


    @Transactional
    public void confirmReport(Long reportId) {
        Report report = reportRepository.findById(reportId)
            .orElseThrow(() -> new ServiceException("confirmReport()", "해당 신고를 찾을 수 없습니다."));

        report.confirm();

        long confirmCount = reportRepository.countByReportedAndState(
            report.getReported(),
            ReportState.CONFIRMED
        );

        if (confirmCount >= 3) {
            memberService.banMember(report.getReported().getId());
        }
    }
}
