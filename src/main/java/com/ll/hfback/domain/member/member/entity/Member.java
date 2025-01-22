package com.ll.hfback.domain.member.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.sql.Date;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Member extends BaseEntity {

    @Column(unique = true, nullable = false, length = 30)
    private String email;

    @Column(nullable = false)
    @JsonIgnore
    private String password;

    @Column(nullable = false)
    private String nickname;  // 닉네임

    private Date birthday;
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
    private State state;  // 회원 상태
    @Getter
    public enum State {
        NORMAL,  // 정상
        DELETED,  // 탈퇴
        BANNED  // 정지
    }

    @Column(nullable = false, length = 20)
    private String role;  // 권한 (관리자, 사용자)


    // 1:N 관계 설정




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
        state = State.DELETED;
    }

    public void restore() {
        state = State.NORMAL;
    }

}
