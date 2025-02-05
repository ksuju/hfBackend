package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.domain.member.auth.entity.SocialAccount;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.LoginType;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.rq.Rq;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthServiceTest {

  @Autowired
  private AuthService authService;

  @Autowired
  private MemberRepository memberRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private Rq rq;

  @Autowired
  private AuthTokenService authTokenService;



  @Test
  @DisplayName("일반 회원가입 - 성공")
  void test_001() {
    // 테스트 데이터
    String email = "test@test.com";
    String password = "password123";
    String nickname = "테스터";

    // 일반 회원가입
    Member member = authService.signup(
        email, password, nickname,
        LoginType.SELF, null, null, null
    );

    // 회원가입 결과
    assertAll(
        () -> assertNotNull(member.getId()),
        () -> assertEquals(email, member.getEmail()),
        () -> assertTrue(passwordEncoder.matches(password, member.getPassword())),
        () -> assertEquals(nickname, member.getNickname()),
        () -> assertEquals(LoginType.SELF, member.getLoginType())
    );

    // DB 저장 검증 결과
    Member savedMember = memberRepository.findByEmail(email).orElseThrow();
    assertAll(
        () -> assertEquals(member.getId(), savedMember.getId()),
        () -> assertEquals(member.getEmail(), savedMember.getEmail()),
        () -> assertEquals(LoginType.SELF, savedMember.getLoginType())
    );
  }


  @Test
  @DisplayName("일반 회원가입 - 필수 입력값(이메일, 비밀번호) 누락 실패")
  void test_002() {
    // 테스트 데이터
    record TestCase(
        String email,
        String password,
        ErrorCode expectedError,
        String description
    ) {}

    TestCase[] testCases = {
        new TestCase(null, "password123", ErrorCode.EMAIL_REQUIRED, "이메일 null"),
        new TestCase("", "password123", ErrorCode.EMAIL_REQUIRED, "이메일 빈문자열"),
        new TestCase("  ", "password123", ErrorCode.EMAIL_REQUIRED, "이메일 공백문자"),
        new TestCase("test@test.com", null, ErrorCode.PASSWORD_REQUIRED, "비밀번호 null"),
        new TestCase("test@test.com", "", ErrorCode.PASSWORD_REQUIRED, "비밀번호 빈문자열"),
        new TestCase("test@test.com", "  ", ErrorCode.PASSWORD_REQUIRED, "비밀번호 공백문자")
    };

    // 일반 회원가입 시도 및 결과
    for (TestCase testCase : testCases) {
      ServiceException exception = assertThrows(
          ServiceException.class,
          () -> authService.signup(
              testCase.email, testCase.password, "테스터",
              LoginType.SELF, null, null, null
          ),
          "테스트 케이스: " + testCase.description
      );

      // 필수 입력값 누락 에러 검증
      assertAll(
          () -> assertEquals(testCase.expectedError, exception.getErrorCode(),
              "테스트 케이스: " + testCase.description),
          () -> assertEquals(400, exception.getErrorCode().getStatus(),
              "테스트 케이스: " + testCase.description)
      );

      // DB 저장 안됨 검증
      if (testCase.email != null && !testCase.email.trim().isEmpty()) {
        assertFalse(memberRepository.findByEmail(testCase.email).isPresent(),
            "테스트 케이스 '" + testCase.description + "'에서 DB에 저장되면 안됨");
      }
    }
  }



  @Test
  @DisplayName("일반 회원가입 - 중복 이메일 회원가입 실패")
  void test_003() {
    // 테스트 데이터 설정
    record TestCase(
        String originalEmail,
        String newEmail,
        ErrorCode expectedError,
        String description
    ) {}

    TestCase testCase = new TestCase(
        "test@test.com",
        "test@test.com",
        ErrorCode.DUPLICATE_EMAIL,
        "이메일 중복"
    );

    // DUPLICATE_EMAIL(409, "MEMBER_002", "이미 사용중인 이메일입니다.")
    authService.signup(
        testCase.originalEmail, "1234", "테스터1",
        LoginType.SELF, null, null, null
    );


    // 중복 이메일로 회원가입 시도
    ServiceException exception = assertThrows(ServiceException.class, () ->
        authService.signup(
            testCase.newEmail, "1234", "테스터2",
            LoginType.SELF, null, null, null
        )
    );

    // 에러 검증
    assertAll(
        () -> assertEquals(testCase.expectedError, exception.getErrorCode(),
            "테스트 케이스: " + testCase.description),
        () -> assertEquals(409, exception.getErrorCode().getStatus(),
            "테스트 케이스: " + testCase.description)
    );
  }


  @Test
  @DisplayName("소셜 로그인 - 최초 소셜 회원가입 성공")
  void test_004() {
    // 테스트 데이터
    record TestCase(
        String email,
        String nickname,
        String providerId,
        String providerEmail,
        String profilePath
    ) {}

    TestCase testCase =
        new TestCase(
        "test@naver.com",
        "소셜테스터",
        "kakao_123456",
        "test@naver.com",
        "profile.jpg"
    );

    // 소셜 로그인 시도
    Member member = authService.handleSocialLogin(
        testCase.email, testCase.nickname,
        LoginType.KAKAO, testCase.profilePath, testCase.providerId
    );

    // DB 결과 검증
    Member savedMember = memberRepository.findById(member.getId()).orElseThrow();
    assertAll(
        () -> assertNotNull(savedMember.getId()),
        () -> assertEquals(testCase.email, savedMember.getEmail()),
        () -> assertNull(savedMember.getPassword()),
        () -> assertEquals(testCase.nickname(), savedMember.getNickname()),
        () -> assertEquals(LoginType.KAKAO, savedMember.getLoginType()),
        () -> assertEquals(testCase.profilePath, savedMember.getProfilePath())
    );

    // 소셜 계정 정보 검증
    SocialAccount socialAccount = savedMember.getSocialAccount();
    assertAll(
        () -> assertNotNull(socialAccount),
        () -> assertEquals(testCase.providerId, socialAccount.getKakaoProviderId()),
        () -> assertEquals(testCase.providerEmail, socialAccount.getKakaoEmail())
    );
  }


  @Test
  @DisplayName("커스텀 로그인  - 토큰에서 회원 정보 추출 성공")
  void test_005() {
    // 테스트 객체 준비
    Member member = authService.signup(
        "test@test.com", "password123", "테스터",
        LoginType.SELF, null, null, null
    );

    member.setProfilePath("profile.jpg");  // DB 처리 기본값 설정
    member.setRole(Member.MemberRole.ROLE_USER);   // DB 처리 기본값 설정
    memberRepository.save(member);

    Member savedMember = memberRepository.findById(member.getId()).orElseThrow();

    // 생성된 토큰으로 회원 정보 추출
    String accessToken = rq.makeAuthCookies(savedMember);
    Member extractedMember = authService.getMemberFromAccessToken(accessToken);

    // 확인
    assertAll(
        () -> assertNotNull(accessToken),
        () -> assertEquals(member.getId(), extractedMember.getId()),
        () -> assertEquals(member.getEmail(), extractedMember.getEmail()),
        () -> assertEquals(member.getNickname(), extractedMember.getNickname())
    );
  }

}
