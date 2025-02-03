package com.ll.hfback.domain.member.member.repository;

import com.ll.hfback.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>  {
    Optional<Member> findByEmail(String email);
    List<Member> findAllByPhoneNumber(String phoneNumber);
    Optional<Member> findByApiKey(String apiKey);

    @Query("SELECT m FROM Member m JOIN m.socialAccount s WHERE s.kakaoProviderId = :providerId")
    Optional<Member> findByKakaoProviderId(@Param("providerId") String providerId);

    @Query("SELECT m FROM Member m JOIN m.socialAccount s WHERE s.googleProviderId = :providerId")
    Optional<Member> findByGoogleProviderId(@Param("providerId") String providerId);

    @Query("SELECT m FROM Member m JOIN m.socialAccount s WHERE s.githubProviderId = :providerId")
    Optional<Member> findByGithubProviderId(@Param("providerId") String providerId);

    @Query("SELECT m FROM Member m JOIN m.socialAccount s WHERE s.naverProviderId = :providerId")
    Optional<Member> findByNaverProviderId(@Param("providerId") String providerId);

}
