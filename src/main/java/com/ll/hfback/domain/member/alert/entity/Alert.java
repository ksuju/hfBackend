package com.ll.hfback.domain.member.alert.entity;

import com.ll.hfback.domain.member.alert.enums.*;
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
public class Alert extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlertDomain domain;  // GROUP, MEMBER, BOARD, FESTIVAL, COMMENT
    @Getter
    @AllArgsConstructor
    public enum AlertDomain {
        GROUP, MEMBER, BOARD, FESTIVAL, COMMENT
    }

    @Column(nullable = false)
    private String alertTypeCode;

    @Column(columnDefinition = "TEXT")
    private String navigationData;  // 클릭 시 이동에 필요한 데이터


    // Entity 메서드
    public void readAlert() {
        isRead = true;
    }

    public NavigationType getNavigationType() {
        return switch (domain) {
            case GROUP -> GroupAlertType.valueOf(alertTypeCode).getNavigationType();
            case MEMBER -> MemberAlertType.valueOf(alertTypeCode).getNavigationType();
            case BOARD -> BoardAlertType.valueOf(alertTypeCode).getNavigationType();
            case FESTIVAL -> FestivalAlertType.valueOf(alertTypeCode).getNavigationType();
            case COMMENT -> CommentAlertType.valueOf(alertTypeCode).getNavigationType();
        };
    }
}
