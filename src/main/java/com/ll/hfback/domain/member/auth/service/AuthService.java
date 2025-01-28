package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.domain.member.auth.entity.SocialAccount;
import com.ll.hfback.domain.member.auth.repository.AuthRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ServiceException;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthTokenService authTokenService;
    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;


    public Member signup(
            String email, String password, String nickname,
            String loginType, String providerId, String profilePath
        ) {
        Member CheckedSignUpMember = authRepository.findByEmail(email);
        if (CheckedSignUpMember != null) {
            throw new ServiceException("이메일 중복", "이미 사용 중인 이메일입니다.");
        }

        Member member = Member.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .loginType(loginType)
                .profilePath(profilePath)
                .apiKey(UUID.randomUUID().toString())
                .build();

        if (providerId != null) {
            member.addSocialAccount(providerId);
        }

        return authRepository.save(member);
    }

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findByApiKey(String apiKey) {
        return authRepository.findByApiKey(apiKey);
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

    public long count() {
        return memberRepository.count();
    }

    public void modify(
            Member member, @NotBlank String nickname, @NotBlank String loginType,
            String providerId, String profilePath
        ) {
        SocialAccount socialAccount = member.getSocialAccounts().stream()
                .filter(account -> account.getProviderId().equals(providerId))
                .findFirst()
                .orElse(null);
        if (socialAccount == null) {
            member.addSocialAccount(providerId);
        }

        member.setNickname(nickname);
        member.setProfilePath(profilePath);

        if (Member.LoginType.isValid(loginType)) {
            member.setLoginType(loginType);
        } else {
            throw new ServiceException("잘못된 로그인 타입", "지원하지 않는 로그인 타입입니다.");
        }
    }

    public Member modifyOrSignup(
            String email, String nickname,
            String loginType, String profilePath, String providerId
        ) {
        Optional<Member> opMember = findByEmail(email);
        if (opMember.isPresent()) {
            Member member = opMember.get();
            modify(member, nickname, loginType, providerId, profilePath);
            return member;
        }
        return signup(email, "", nickname, loginType, providerId, profilePath);
    }
}
