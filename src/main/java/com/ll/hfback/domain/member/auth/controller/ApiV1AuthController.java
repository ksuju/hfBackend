package com.ll.hfback.domain.member.auth.controller;

import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.dto.SignupResponse;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.dto.MemberDto;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.rq.Rq;
import com.ll.hfback.global.rsData.RsData;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiV1AuthController {

    private final AuthService authService;
    private final Rq rq;
    private final PasswordEncoder passwordEncoder;


    record LoginRequest (
        @NotBlank
        String email,
        @NotBlank
        String password
    ) {
    }

    record LoginResponse (
        @NonNull MemberDto item,
        @NonNull
        String apiKey,
        @NonNull
        String accessToken
    ) {
    }

    // MEM01_LOGIN01 : 로그인
    @PostMapping("/login")
    public RsData<LoginResponse> login(
            @Valid @RequestBody LoginRequest request
        ) {
        Member member = authService
            .findByEmail(request.email)
            .orElseThrow(() -> new ServiceException("401-1", "존재하지 않는 사용자입니다."));

        if (!passwordEncoder.matches(request.password, member.getPassword())) {
            throw new ServiceException("401-2", "비밀번호가 일치하지 않습니다.");
        }

        String accessToken = rq.makeAuthCookies(member);

        return new RsData<>(
            "200-1",
            "%s님 환영합니다.".formatted(member.getNickname()),
            new LoginResponse(
                new MemberDto(member),
                member.getApiKey(),
                accessToken
            )
        );
    }


    // MEM01_LOGIN03 : 아이디 저장 (이메일)
    //@PostMapping("/save-id")


    // MEM01_LOGIN04 : 아이디 찾기 (이메일)
    //@PostMapping("/find-id")


    // MEM01_LOGIN05 : 비밀번호 재설정
    //@PostMapping("/reset-password")


    // MEM02_SIGNUP01 : 회원가입
    @PostMapping("/signup")
    public RsData<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        Member member = authService.signup(
            request.getEmail(), request.getPassword(), request.getNickname(),
            Member.LoginType.SELF, null, null
        );

        return new RsData<>(
            "201-1",
            "회원가입 성공!  %s님 환영합니다.".formatted(request.getNickname()),
            new SignupResponse(member)
        );
    }

    // MEM02_SIGNUP02 : 이메일 인증
    //@PostMapping("/email-verify")


    // MEM02_SIGNUP03 : 도로명 주소 찾기
    //@PostMapping("/address-verify")


    // MEM06_LOGOUT : 로그아웃
    @GetMapping("/logout")
    public RsData<Void> logout(HttpServletResponse response) {
        rq.deleteCookie("accessToken");
        rq.deleteCookie("apiKey");

        return new RsData<>("200-1", "로그아웃 되었습니다.");
    }
}
