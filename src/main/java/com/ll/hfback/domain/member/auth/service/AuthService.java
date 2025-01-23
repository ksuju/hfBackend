package com.ll.hfback.domain.member.auth.service;

import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.repository.AuthRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;
    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

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

        return authRepository.save(member);
    }

}
