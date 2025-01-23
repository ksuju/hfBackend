package com.ll.hfback.domain.member.auth.controller;

import com.ll.hfback.domain.member.auth.dto.LoginRequest;
import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.dto.SignupResponse;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.jwt.JwtProvider;
import com.ll.hfback.global.rsData.RsData;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiV1AuthController {

    private final AuthService authService;
    private final MemberService memberService;
    private final JwtProvider jwtProvider;

    // MEM01_LOGIN01 : 자체 로그인
    @PostMapping("/login")
    public RsData<Void> login(
            @Valid @RequestBody LoginRequest request,
            HttpServletResponse response
        ) {
        Member member = memberService.getMember(request.getEmail());
        String token = jwtProvider.genAccessToken(member);

        response.addCookie(new Cookie("accessToken", token));


        return new RsData<>("200", "로그인에 성공하였습니다.");
    }

    // MEM01_LOGIN02 : 구글 소셜 로그인
    //@PostMapping("/google")


    // MEM01_LOGIN03 : 아이디 저장 (이메일)
    //@PostMapping("/save-id")


    // MEM01_LOGIN04 : 아이디 찾기 (이메일)
    //@PostMapping("/find-id")


    // MEM01_LOGIN05 : 비밀번호 재설정
    //@PostMapping("/reset-password")


    // MEM02_SIGNUP01 : 자체 회원가입
    @PostMapping("/signup")
    public RsData<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        Member member = authService.signup(request);
        return new RsData<>("200", "회원가입에 성공하였습니다.", new SignupResponse(member));
    }

    // MEM02_SIGNUP02 : 이메일 인증
    //@PostMapping("/email-verify")


    // MEM02_SIGNUP03 : 도로명 주소 찾기
    //@PostMapping("/address-verify")


    // MEM06_LOGOUT : 로그아웃
    @GetMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        //authService.logout(token);
    }
}
