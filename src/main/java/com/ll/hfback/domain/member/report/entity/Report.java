package com.ll.hfback.domain.member.report.entity;

import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Report extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_id", nullable = false)
    private Member reporter;  // 신고자

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reported_id", nullable = false)
    private Member reported;  // 신고대상자

    @Column(nullable = false)
    private String content;  // 신고 내용

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('WAITING', 'CONFIRMED', 'REJECTED') DEFAULT 'WAITING'")
    private ReportState state;  // 신고 상태 (
    public enum ReportState {
        WAITING, CONFIRMED, REJECTED
    }


    // 관련 메서드
    public void confirm() {
        state = ReportState.CONFIRMED;
    }

    public void reject() {
        state = ReportState.REJECTED;
    }
}
