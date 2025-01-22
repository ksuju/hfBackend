package com.ll.hfback.domain.member.report.repository;

import com.ll.hfback.domain.member.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
