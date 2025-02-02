package com.ll.hfback.domain.member.auth.entity;

import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.LoginType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SocialAccount {
  @Id
  private Long id;

  @MapsId
  @OneToOne
  @JoinColumn(name = "id")
  @EqualsAndHashCode.Include
  private Member member;

  // Kakao
  @Column(unique = true, length = 50)
  private String kakaoProviderId;
  @Column(unique = true, length = 50)
  private String kakaoEmail;
  @Column(nullable = false)
  @Builder.Default
  private boolean kakaoActive = false;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime kakaoCreateDate;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime kakaoModifyDate;

  // Google
  @Column(length = 50, unique = true)
  private String googleProviderId;
  @Column(unique = true, length = 50)
  private String googleEmail;
  @Column(nullable = false)
  @Builder.Default
  private boolean googleActive = false;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime googleCreateDate;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime googleModifyDate;

  // Github
  @Column(length = 50, unique = true)
  private String githubProviderId;
  @Column(unique = true, length = 50)
  private String githubEmail;
  @Column(nullable = false)
  @Builder.Default
  private boolean githubActive = false;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime githubCreateDate;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime githubModifyDate;

  // Naver
  @Column(length = 150, unique = true)
  private String naverProviderId;
  @Column(unique = true, length = 50)
  private String naverEmail;
  @Column(nullable = false)
  @Builder.Default
  private boolean naverActive = false;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime naverCreateDate;
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime naverModifyDate;


  // 공통
  public boolean hasSocialAccount(String provider) {
    if (provider == null) {
      return false;
    }

    return switch (provider) {
      case LoginType.KAKAO -> kakaoActive;
      case LoginType.GOOGLE -> googleActive;
      case LoginType.GITHUB -> githubActive;
      case LoginType.NAVER -> naverActive;
      default -> false;
    };
  }


  // Kakao
  public void updateKakaoInfo(String providerId, String email, boolean active) {
    kakaoProviderId = providerId;
    kakaoEmail = email;
    kakaoActive = active;
    kakaoModifyDate = LocalDateTime.now();
  }

  public void connectKakao(String providerId, String email) {
    if (kakaoCreateDate == null) {
      kakaoCreateDate = LocalDateTime.now();
    }
    updateKakaoInfo(providerId, email, true);
  }

  public void disconnectKakao() {
    updateKakaoInfo(null, null, false);
  }


  // Google
  public void updateGoogleInfo(String providerId, String email, boolean active) {
    googleProviderId = providerId;
    googleEmail = email;
    googleActive = active;
    googleModifyDate = LocalDateTime.now();
  }

  public void connectGoogle(String providerId, String email) {
    if (googleCreateDate == null) {
      googleCreateDate = LocalDateTime.now();
    }
    updateGoogleInfo(providerId, email, true);
  }

  public void disconnectGoogle() {
    updateGoogleInfo(null, null, false);
  }


  // Github
  public void updateGithubInfo(String providerId, String email, boolean active) {
    githubProviderId = providerId;
    githubEmail = email;
    githubActive = active;
    githubModifyDate = LocalDateTime.now();
  }

  public void connectGithub(String providerId, String email) {
    if (githubCreateDate == null) {
      githubCreateDate = LocalDateTime.now();
    }
    updateGithubInfo(providerId, email, true);
  }

  public void disconnectGithub() {
    updateGithubInfo(null, null, false);
  }


  // Naver
  public void updateNaverInfo(String providerId, String email, boolean active) {
    naverProviderId = providerId;
    naverEmail = email;
    naverActive = active;
    naverModifyDate = LocalDateTime.now();
  }

  public void connectNaver(String providerId, String email) {
    if (naverCreateDate == null) {
      naverCreateDate = LocalDateTime.now();
    }
    updateNaverInfo(providerId, email, true);
  }

  public void disconnectNaver() {
    updateNaverInfo(null, null, false);
  }
}
