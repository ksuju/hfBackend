package com.ll.hfback.domain.member.auth.controller;

import com.ll.hfback.domain.member.auth.dto.LoginRequest;
import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiV1AuthController {

    private final AuthService authService;

    // MEM01_LOGIN01 : 자체 로그인
    @PostMapping("/login")
    public void login(@RequestBody LoginRequest request) {
        // authService.login(request)
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
    public void signup(@RequestBody SignupRequest request) {
        // authService.signup(request)
    }

    // MEM02_SIGNUP02 : 이메일 인증
    //@PostMapping("/email-verify")


    // MEM02_SIGNUP03 : 도로명 주소 찾기
    //@PostMapping("/address-verify")


    // MEM06_LOGOUT : 로그아웃
    @PostMapping("/logout")
    public void logout(@RequestHeader("Authorization") String token) {
        //authService.logout(token);
    }
}
