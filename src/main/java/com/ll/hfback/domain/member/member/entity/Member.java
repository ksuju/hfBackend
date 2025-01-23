package com.ll.hfback.domain.member.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.member.alert.entity.Alert;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.report.entity.Report;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.DynamicInsert;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
public class Member extends BaseEntity {

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String nickname;  // 닉네임

    private LocalDate birthday;
    private String location;  // 주소

    @Enumerated(EnumType.STRING)
    @Column(length = 1, nullable = false)
    private Gender gender;  // 성별
    @Getter
    public enum Gender {
        M, W;
    }

    @Column(nullable = false, length = 20)
    private String phoneNumber;  // 전화번호

    @Column(columnDefinition = "VARCHAR(255) DEFAULT 'default.png'")
    private String profilePath;  // 프로필 사진 경로

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT TRUE")
    private boolean mkAlarm;  // 마케팅 수신여부

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('NORMAL', 'DELETED', 'BANNED') DEFAULT 'NORMAL'")
    private MemberState state;  // 회원 상태
    @Getter
    public enum MemberState {
        NORMAL,  // 정상
        DELETED,  // 탈퇴
        BANNED  // 정지
    }

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('USER', 'ADMIN') DEFAULT 'USER'")
    private MemberRole role;  // 권한 (관리자, 사용자)
    @Getter
    public enum MemberRole {
        USER,
        ADMIN
    }

    @JsonIgnore
    String refreshToken;




    // 1대1 관계 설정







    // 1:N 관계 설정
    @JsonIgnore
    @OneToMany(mappedBy = "member", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    private List<Alert> alerts = new ArrayList<>();

    public void addAlert(
        String content, String url, String category
    ) {
        Alert alert = Alert.builder()
            .member(this)
            .content(content)
            .url(url)
            .category(category)
            .build();
        alerts.add(alert);
    }

    public void removeAlert(Alert alert) {
        alerts.remove(alert);
    }


    @JsonIgnore
    @OneToMany(mappedBy = "reporter", cascade = ALL, orphanRemoval = true) // fetch = FetchType.LAZY
    @Builder.Default
    private List<Report> reports = new ArrayList<>();

    public void addReport(
        Member reported, String content
    ) {
        Report report = Report.builder()
            .reporter(this)
            .reported(reported)
            .content(content)
            .build();
        reports.add(report);
    }

    public void removeReport(Report report) {
        reports.remove(report);
    }

    // Entity 메서드
    public void updateInfo(MemberUpdateRequest request) {
        email = request.getEmail();
        gender = request.getGender();
        nickname = request.getNickname();
        password = request.getPassword();
        phoneNumber = request.getPhoneNumber();
        mkAlarm = request.isMkAlarm();
        birthday = request.getBirthday();
        location = request.getLocation();
        profilePath = request.getProfilePath();
    }

    public void deactivate() {
        state = MemberState.DELETED;
    }

    public void restore() {
        state = MemberState.NORMAL;
    }

}
