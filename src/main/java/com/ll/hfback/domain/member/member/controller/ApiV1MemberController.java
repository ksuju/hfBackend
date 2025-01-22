package com.ll.hfback.domain.member.member.controller;

import com.ll.hfback.domain.member.member.dto.MemberDto;
import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.rsData.RsData;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class ApiV1MemberController {

    private final MemberService memberService;


    // MEM03_MODIFY01 : 회원정보 수정 전 비밀번호 인증
    // @PostMapping("/{memberId}/verify-password")



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


    // MEM07_CONTROL1 : 회원 목록 (관리자)
    @GetMapping
    public List<MemberDto> getMembers() {
        List<Member> members = memberService.findAll();
        return members.stream().map(MemberDto::new).toList();
    }


    // MEM07_CONTROL2 : 회원 상세 조회 (관리자)
    @GetMapping("/{memberId}")
    public MemberDto getMember(@PathVariable Long memberId) {
        Member member = memberService.findById(memberId).orElse(null);
        return new MemberDto(member);
    }


    // MEM07_CONTROL3 : 회원 복구 (관리자)
    @PatchMapping("/{memberId}/restore")
    public RsData<Void> restoreMember(@PathVariable Long memberId) {
        memberService.restoreMember(memberId);
        return new RsData<>("200", "회원 복구가 성공하였습니다.");
    }
}
