package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.domain.member.auth.dto.EmailInfo;
import com.ll.hfback.domain.member.auth.dto.FindEmailsResponse;
import com.ll.hfback.domain.member.auth.entity.SocialAccount;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.LoginType;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenService authTokenService;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Member signup(
            String email, String password, String nickname,
            String loginType, String providerId, String providerEmail, String profilePath
        ) {
        _validateEmailAndPassword(email, password, loginType);

        Optional<Member> existingMember = memberRepository.findByEmail(email);
        if (existingMember.isPresent()) {
            throw new ServiceException(ErrorCode.DUPLICATE_EMAIL);
        }

        Member member = Member.builder()
                .email(email)
                .password(
                    loginType.equals(LoginType.SELF) ? passwordEncoder.encode(password) : null
                )
                .nickname(nickname)
                .loginType(loginType)
                .profilePath(profilePath)
                .apiKey(UUID.randomUUID().toString())
                .role(Member.MemberRole.ROLE_USER)
                .state(Member.MemberState.NORMAL)
                .build();

        if (providerId != null) {
            _connectSocialAccount(member, loginType, providerId, providerEmail);
        }

        return memberRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByApiKey(String apiKey) {
        return memberRepository.findByApiKey(apiKey);
    }

    public String genAccessToken(Member member) {
        return authTokenService.genAccessToken(member);
    }

    public String genAuthToken(Member member) {
        return member.getApiKey() + " " + genAccessToken(member);
    }

    public Member getMemberFromAccessToken(String accessToken) {
        Map<String, Object> payload = authTokenService.payload(accessToken);
        if (payload == null) {
            return null;
        }
        long id = (long) payload.get("id");
        String email = (String) payload.get("email");
        String nickname = (String) payload.get("nickname");
        String profilePath = (String) payload.get("profilePath");
        String role = (String) payload.get("role");

        return new Member(id, email, nickname, profilePath, Member.MemberRole.valueOf(role));
    }


    public FindEmailsResponse findEmailsByPhoneNumber(String phoneNumber) {
        List<Member> members = memberRepository.findAllByPhoneNumber(phoneNumber);
        if (members.isEmpty()) {
            throw new ServiceException(ErrorCode.INVALID_PHONE_NUMBER);
        }

        List<EmailInfo> emailInfos = members.stream()
            .map(member -> new EmailInfo(
                Ut.str.maskEmail(member.getEmail()),
                member.getCreateDate()
            ))
            .toList();

        return new FindEmailsResponse(emailInfos);
    }


    public long count() {
        return memberRepository.count();
    }


    @Transactional
    public Member handleSocialLogin(
        String email, String nickname,
        String loginType, String profilePath, String providerId
    ) {
        Member connectedMember = _findByProviderId(loginType, providerId);
        if (connectedMember != null) {
            connectedMember.setLoginType(loginType);
            return connectedMember;  // 연결된 계정이 있다면 그 계정으로 로그인
        }

        Optional<Member> existingMember = findByEmail(email); // 이메일이 사용중인지 확인
        if (existingMember.isPresent()) {
            Member member = existingMember.get();
            _connectSocialAccount(member, loginType, providerId, email);  // 기존 계정에 소셜 계정 연결
            return member;
        }

        return signup(email, null, nickname, loginType, providerId, email, profilePath);
    }

    @Transactional
    public void connectSocial(
            Member loginUser, String loginType, String providerId, String providerEmail
        ) {
        Member connectedMember = _findByProviderId(loginType, providerId);
        if (connectedMember != null) {
            throw new ServiceException(ErrorCode.SOCIAL_ACCOUNT_ALREADY_IN_USE);
        }
        _connectSocialAccount(loginUser, loginType, providerId, providerEmail);
    }



    private void _connectSocialAccount(
        Member member, String loginType,
        String providerId, String providerEmail
    ) {
        SocialAccount socialAccount = member.getSocialAccountOrCreate();

        switch (loginType) {
            case LoginType.KAKAO -> socialAccount.connectKakao(providerId, providerEmail);
            case LoginType.GOOGLE -> socialAccount.connectGoogle(providerId, providerEmail);
            case LoginType.GITHUB -> socialAccount.connectGithub(providerId, providerEmail);
            case LoginType.NAVER -> socialAccount.connectNaver(providerId, providerEmail);
        }
    }


    private Member _findByProviderId(String loginType, String providerId) {
        return switch (loginType) {
            case LoginType.KAKAO -> memberRepository.findByKakaoProviderId(providerId).orElse(null);
            case LoginType.GOOGLE -> memberRepository.findByGoogleProviderId(providerId).orElse(null);
            case LoginType.GITHUB -> memberRepository.findByGithubProviderId(providerId).orElse(null);
            case LoginType.NAVER -> memberRepository.findByNaverProviderId(providerId).orElse(null);
            default -> null;
        };
    }


    private void _validateEmailAndPassword(String email, String password, String loginType) {
        if (email == null || email.trim().isEmpty()) {
            throw new ServiceException(ErrorCode.EMAIL_REQUIRED);
        }

        if (LoginType.SELF.equals(loginType) && (password == null || password.trim().isEmpty())) {
            throw new ServiceException(ErrorCode.PASSWORD_REQUIRED);
        }
    }

}
