package com.ll.hfback.domain.member.member.controller;

import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;

    // MEM03_MODIFY01 : 회원정보 수정 전 비밀번호 인증


    // MEM03_MODIFY02 : 회원정보 수정


    // MEM03_MODIFY03 : 전화번호 인증


    // MEM05_DELETE : 회원 탈퇴


    // MEM07_CONTROL1 : 회원 목록 (관리자)

    // MEM07_CONTROL2 : 회원 상세 조회 (관리자)


    // MEM07_CONTROL3 : 회원 복구 (관리자)


}
