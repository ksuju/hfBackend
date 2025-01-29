package com.ll.hfback.domain.member.member.controller;

import com.ll.hfback.domain.member.member.dto.MemberDto;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.standard.base.Empty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;


    // MEM03_MODIFY01 : 회원정보 수정 전 비밀번호 인증
    @PostMapping("/{memberId}/verify-password")
    public RsData<Void> verifyPassword(
        @PathVariable("memberId") Long memberId,
        HttpServletRequest request
    ) {

        return new RsData<>("200", "비밀번호 인증이 성공하였습니다.");
    }


    // MEM03_MODIFY02 : 회원정보 수정
    @PutMapping("/{memberId}")
    public RsData<MemberDto> updateMember(
        @PathVariable Long memberId,
        @Valid @RequestBody MemberUpdateRequest memberUpdateRequest
    ) {
        Member member = memberService.findById(memberId).orElse(null);
        Member modifiedMember = memberService.modify(member, memberUpdateRequest);
        return new RsData<>("200", "회원 정보 업데이트가 성공하였습니다.", new MemberDto(modifiedMember));
    }


    // MEM03_MODIFY03 : 전화번호 인증
    // @PostMapping("/{memberId}/verify-phone")



    // MEM05_DELETE : 회원 탈퇴
    @PatchMapping("/{memberId}/deactivate")
    public RsData<Void> deactivateMember(@PathVariable Long memberId) {
        memberService.deactivateMember(memberId);
        return new RsData<>("200", "회원 탈퇴가 성공하였습니다.");
    }


    // MEMCTL01_CONTROL1 : 회원 목록 (관리자)
    @GetMapping
    public List<MemberDto> getMembers() {
        List<Member> members = memberService.findAll();
        return members.stream().map(MemberDto::new).toList();
    }


    // MEMCTL01_CONTROL2 : 회원 상세 조회 (관리자)
    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId).orElse(null);
        return new MemberDto(member);
    }


    // MEMCTL01_CONTROL3 : 회원 복구 (관리자)
    @PatchMapping("/{memberId}/restore")
    public RsData<Void> restoreMember(@PathVariable Long memberId) {
        memberService.restoreMember(memberId);
        return new RsData<>("200", "회원 복구가 성공하였습니다.");
    }


    // MEMCTL01_CONTROL4 : 회원 차단 처리 (관리자)
    @PatchMapping("/{memberId}/block")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RsData<Empty> banMember(@PathVariable Long memberId) {
        memberService.banMember(memberId);
        return new RsData("200-1", "%d번 회원을 차단했습니다.".formatted(memberId));
    }

}
