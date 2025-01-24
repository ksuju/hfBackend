package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.repository.AuthRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.jwt.JwtProvider;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.security.SecurityUser;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;
    private final JwtProvider jwtProvider;

    public Member signup(SignupRequest request) {
        Member CheckedSignUpMember = memberRepository.findByEmail(request.getEmail());
        if (CheckedSignUpMember != null) {
            throw new IllegalArgumentException("이미 존재하는 회원입니다.");
        }

        Member member = Member.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .nickname(request.getNickname())
                .gender(request.getGender())
                .mkAlarm(request.isMkAlarm())
                .phoneNumber(request.getPhoneNumber())
                .build();

        String refreshToken = jwtProvider.genRefreshToken(member);
        member.setRefreshToken(refreshToken);

        return authRepository.save(member);
    }

    public boolean validateToken(String token) {
        return jwtProvider.verify(token);
    }

    public RsData<String> refreshAccessToken(String refreshToken) {
        Member member = memberRepository.findByRefreshToken(refreshToken)
            .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 토큰입니다."));

        String accessToken = jwtProvider.genAccessToken(member);

        return new RsData<>("200", "토큰 갱신에 성공하였습니다.", accessToken);
    }

    public SecurityUser getUserFromAccessToken(String accessToken) {
        Map<String, Object> payloadBody = jwtProvider.getClaims(accessToken);
        long id = (int) payloadBody.get("id");
        String email = (String) payloadBody.get("email");
        List<GrantedAuthority> authorities = new ArrayList<>();
        return new SecurityUser(id, email, "", authorities);
    }

}
