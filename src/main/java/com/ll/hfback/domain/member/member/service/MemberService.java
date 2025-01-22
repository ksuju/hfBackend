package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.member.dto.MemberUpdateRequest;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public Member modify(Member member, MemberUpdateRequest memberUpdateRequest) {
        member.updateInfo(memberUpdateRequest);
        return member;
    }

    @Transactional
    public void deactivateMember(Long id) {
        memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id + "번 사용자가 존재하지 않습니다."))
            .deactivate();
    }

    @Transactional
    public void restoreMember(Long id) {
        memberRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException(id + "번 사용자가 존재하지 않습니다."))
            .restore();
    }
}
