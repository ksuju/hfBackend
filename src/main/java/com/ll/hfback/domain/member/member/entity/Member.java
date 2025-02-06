package com.ll.hfback.domain.member.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.group.chatRoom.converter.StringListConverter;
import com.ll.hfback.domain.member.alert.entity.Alert;
import com.ll.hfback.domain.member.auth.entity.SocialAccount;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.dto.SocialAccountStatus;
import com.ll.hfback.domain.member.report.entity.Report;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import lombok.experimental.UtilityClass;
import org.hibernate.annotations.DynamicInsert;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDate;
import java.util.*;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@DynamicInsert
public class Member extends BaseEntity {

    // 참여하고 있는 모임채팅방ID 리스트
    @Convert(converter = StringListConverter.class)
    private List<String> joinRoomIdList;

    // 대기하고 있는 모임채팅방ID 리스트
    @Convert(converter = StringListConverter.class)
    private List<String> waitRoomIdList;

    @Column(unique = true, nullable = false, length = 30)
    private String email;


    @JsonIgnore
    private String password;

    public boolean hasPassword() {
        return password != null && !password.isEmpty();
    }


    @Column(nullable = false, unique = true)
    private String nickname;  // 닉네임


    private LocalDate birthday;
    private String location;  // 주소


    @Enumerated(EnumType.STRING)
    @Column(length = 1)
    private Gender gender;  // 성별
    @Getter
    public enum Gender {
        M, W;
    }


    @Column(length = 20)
    private String phoneNumber;  // 전화번호


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

    public void deactivate() {
        state = MemberState.DELETED;
    }

    public void restore() {
        state = MemberState.NORMAL;
    }


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, columnDefinition = "ENUM('ROLE_USER', 'ROLE_ADMIN') DEFAULT 'ROLE_USER'")
    private MemberRole role = MemberRole.ROLE_USER;  // 권한 (관리자, 사용자)
    @Getter
    public enum MemberRole {
        ROLE_USER, ROLE_ADMIN
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role != null) {
            return Collections.singletonList(new SimpleGrantedAuthority(this.role.name()));
        } else {
            return Collections.singletonList(new SimpleGrantedAuthority(MemberRole.ROLE_USER.name()));
        }
    }



    @JsonIgnore
    @Column(unique = true, length = 50)
    private String apiKey;


    @Column(nullable = false, columnDefinition = "VARCHAR(50) DEFAULT 'SELF'")
    private String loginType = LoginType.SELF;  // 현재 로그인 방식
    @UtilityClass
    public class LoginType {
        public static final String SELF = "SELF";
        public static final String NAVER = "NAVER";
        public static final String KAKAO = "KAKAO";
        public static final String GOOGLE = "GOOGLE";
        public static final String GITHUB = "GITHUB";

        public static boolean isValid(String loginType) {
            return SELF.equals(loginType) || NAVER.equals(loginType)
                || KAKAO.equals(loginType) || GOOGLE.equals(loginType) || GITHUB.equals(loginType);
        }
    }




    // 1대1 관계 설정
    @JsonIgnore
    @OneToOne(mappedBy = "member", cascade = ALL, orphanRemoval = true)
    private SocialAccount socialAccount;

    public SocialAccount getSocialAccountOrCreate() {
        if (socialAccount == null) {
            socialAccount = SocialAccount.builder()
                .member(this)
                .build();
        }
        return socialAccount;
    }

    public boolean hasSocialAccount(String provider) {
        return socialAccount != null && socialAccount.hasSocialAccount(provider);
    }

    public int getConnectedSocialCount() {
        if (socialAccount == null) return 0;

        int count = 0;
        if (socialAccount.isKakaoActive()) count++;
        if (socialAccount.isGoogleActive()) count++;
        if (socialAccount.isNaverActive()) count++;
        if (socialAccount.isGithubActive()) count++;
        return count;
    }

    public void disconnectSocialAccount(String provider) {
        if (socialAccount == null) {
            throw new ServiceException(ErrorCode.NOT_CONNECTED_SOCIAL_ACCOUNT);
        }

        switch (provider) {
            case LoginType.KAKAO -> socialAccount.disconnectKakao();
            case LoginType.GOOGLE -> socialAccount.disconnectGoogle();
            case LoginType.GITHUB -> socialAccount.disconnectGithub();
            case LoginType.NAVER -> socialAccount.disconnectNaver();
            default -> throw new ServiceException(ErrorCode.DISCONNECT_FAIL);
        }
    }


    public Map<String, SocialAccountStatus> getSocialAccountStatuses() {
        Map<String, SocialAccountStatus> statuses = new HashMap<>();
        if (socialAccount != null) {
            // Kakao
            statuses.put(LoginType.KAKAO, SocialAccountStatus.builder()
                .createDate(socialAccount.getKakaoCreateDate())
                .email(socialAccount.getKakaoEmail())
                .active(socialAccount.isKakaoActive())
                .build());

            // Google
            statuses.put(LoginType.GOOGLE, SocialAccountStatus.builder()
                .createDate(socialAccount.getGoogleCreateDate())
                .email(socialAccount.getGoogleEmail())
                .active(socialAccount.isGoogleActive())
                .build());

            // Github
            statuses.put(LoginType.GITHUB, SocialAccountStatus.builder()
                .createDate(socialAccount.getGithubCreateDate())
                .email(socialAccount.getGithubEmail())
                .active(socialAccount.isGithubActive())
                .build());

            // Naver
            statuses.put(LoginType.NAVER, SocialAccountStatus.builder()
                .createDate(socialAccount.getNaverCreateDate())
                .email(socialAccount.getNaverEmail())
                .active(socialAccount.isNaverActive())
                .build());
        }

        return statuses;
    }









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

    public List<Report> getReports() {
        return Collections.unmodifiableList(reports);  // 수정 불가능 리스트 반환
    }

    public void addReport(Report report) { reports.add(report); }

    public void removeReport(Report report) {
        reports.remove(report);
    }




    // Entity 메서드
    public Member(long id, String email, String nickname, String profilePath, MemberRole role) {
        this.setId(id);
        this.email = email;
        this.nickname = nickname;
        this.profilePath = profilePath;
        this.role = role;
    }

    public void updateInfo(MemberUpdateRequest request) {
        gender = request.getGender();
        nickname = request.getNickname();
        mkAlarm = request.isMkAlarm();
        birthday = request.getBirthday();
        location = request.getLocation();
        phoneNumber = request.getPhoneNumber();
    }

}
