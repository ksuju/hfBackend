package com.ll.hfback.domain.member.auth.repository;

import com.ll.hfback.domain.member.auth.entity.SocialAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialAccountRepository extends JpaRepository<SocialAccount, Long> {
  boolean existsByKakaoProviderId(String kakaoProviderId);
  boolean existsByGoogleProviderId(String googleProviderId);
  boolean existsByGithubProviderId(String githubProviderId);
  boolean existsByNaverProviderId(String naverProviderId);
}
