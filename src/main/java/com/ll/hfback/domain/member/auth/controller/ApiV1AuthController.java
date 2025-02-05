package com.ll.hfback.domain.member.auth.controller;


import com.ll.hfback.domain.member.auth.dto.*;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.auth.service.EmailService;
import com.ll.hfback.domain.member.member.dto.MemberDto;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.PasswordService;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.rq.Rq;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class ApiV1AuthController {

    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordService passwordService;
    private final Rq rq;
    private final PasswordEncoder passwordEncoder;
    private final StringRedisTemplate redisTemplate;


    // [1. 인증/인가 프로세스]
    record LoginRequest (
        @NotBlank
        String email,
        @NotBlank
        String password
    ) {}

    record LoginResponse (
        @NonNull MemberDto item,
        @NonNull
        String apiKey,
        @NonNull
        String accessToken
    ) {}

    // AUTH01_LOGIN01 : 로그인   (AUTH01_LOGIN02 : 소셜 로그인 - OAuth2)
    @PostMapping("/login")
    public RsData<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        Member member = authService
            .findByEmail(request.email)
            .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_EMAIL));

        if (!member.hasPassword()) {
            throw new ServiceException(ErrorCode.SOCIAL_ONLY_ACCOUNT);
        }

        if (!passwordEncoder.matches(request.password, member.getPassword())) {
            throw new ServiceException(ErrorCode.INVALID_PASSWORD);
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


    // AUTH01_LOGIN04 : 현재 로그인 사용자 정보 조회
    @GetMapping("/me")
    public RsData<LoginUserDto> me(@LoginUser Member loginUser) {
        Member member = authService
            .findByEmail(loginUser.getEmail())
            .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_EMAIL));

        return new RsData("200", "회원정보 조회 성공", LoginUserDto.of(member));
    }


    // AUTH01_LOGIN05 : 로그아웃
    @PostMapping("/logout")
    public RsData<Void> logout() {
        rq.deleteCookie("accessToken");
        rq.deleteCookie("apiKey");

        return new RsData<>("200-1", "로그아웃 되었습니다.");
    }




    // [2. 회원가입 프로세스]
    // AUTH02_SIGNUP01 : 회원가입
    @PostMapping("/signup")
    public RsData<SignupResponse> signup(@Valid @RequestBody SignupRequest request) {
        String verified = redisTemplate.opsForValue().get("email:verify:" + request.getEmail());
        if (verified == null) {
            throw new ServiceException(ErrorCode.EMAIL_NOT_VERIFIED);
        }

        Member member = authService.signup(
            request.getEmail(), request.getPassword(), request.getNickname(),
            Member.LoginType.SELF, null, null, null
        );

        return new RsData<>(
            "201-1",
            "회원가입 성공!  %s님 환영합니다.".formatted(request.getNickname()),
            new SignupResponse(member)
        );
    }


    // AUTH02_SIGNUP02 : 이메일 인증 코드 발송
    @PostMapping("/email/verification-code")
    public RsData<Void> sendVerificationEmail(@Valid @RequestBody EmailRequest request) {
        emailService.sendVerificationEmail(request.email());
        return new RsData<>("200-1", "입력하신 이메일로 인증 코드가 발송되었습니다.");
    }


    // AUTH02_SIGNUP03 : 이메일 인증 코드 확인
    @PostMapping("/email/verify")
    public RsData<Void> verifyEmail(@Valid @RequestBody EmailVerifyRequest request) {
        boolean isValid = emailService.verifyCode(request.email(), request.code());
        if (!isValid) {
            throw new ServiceException(ErrorCode.EMAIL_VERIFICATION_NOT_MATCH);
        }

        redisTemplate.opsForValue()
            .set("email:verify:" + request.email(), "verified", 3, TimeUnit.MINUTES);
        return new RsData<>("200-1", "이메일 인증이 완료되었습니다.");
    }




    // [3. 계정 복구 프로세스]
    // AUTH03_RECOVER01 : 아이디 찾기 (이메일)
    @PostMapping("/find-account")
    public RsData<FindEmailsResponse> findEmail(@Valid @RequestBody FindEmailsRequest request) {
        FindEmailsResponse response = authService.findEmailsByPhoneNumber(request.phoneNumber());

        return new RsData<>(
            "200-1",
            "전화번호로 등록된 계정을 찾았습니다.",
            response
        );
    }


    // AUTH03_RECOVER02 : 1. 비밀번호 찾기 => 비밀번호 재설정 요청 (이메일로 인증코드 발송)
    @PostMapping("/password/reset")
    public RsData<Void> requestPasswordReset(@Valid @RequestBody PasswordResetRequest request) {
        Member member = authService.findByEmail(request.email())
            .orElseThrow(() -> new ServiceException(ErrorCode.INVALID_EMAIL));

        emailService.sendVerificationEmail(member.getEmail());
        return new RsData<>("200-1", "인증 코드가 이메일로 발송되었습니다.");
    }


    // AUTH03_RECOVER03 : 2. 인증코드 확인 및 인증 상태를 Redis에 저장
    @PostMapping("/password/reset/verify")
    public RsData<Void> verifyResetCode(@Valid @RequestBody
    PasswordResetVerifyRequest request) {
        boolean isValid = emailService.verifyCode(request.email(), request.code());
        if (!isValid) {
            throw new ServiceException(ErrorCode.EMAIL_VERIFICATION_NOT_MATCH);
        }

        redisTemplate.opsForValue()
            .set("pwd:reset:" + request.email(), "verified", 3, TimeUnit.MINUTES);
        return new RsData<>("200-1", "인증이 완료되었습니다.");
    }


    // AUTH03_RECOVER04 : 3. Redis에서 값을 꺼내서 확인 후 새 비밀번호 설정
    @PatchMapping("/password/reset/new")
    public RsData<Void> setNewPassword(@Valid @RequestBody SetNewPasswordRequest request) {
        String verified = redisTemplate.opsForValue().get("pwd:reset:" + request.email());
        if (verified == null) {
            throw new ServiceException(ErrorCode.PASSWORD_RESET_NOT_VERIFIED);
        }

        passwordService.resetPassword(request.email(), request.newPassword());
        return new RsData<>("200-1", "비밀번호가 재설정되었습니다.");
    }
}
