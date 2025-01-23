package com.ll.hfback.domain.member.member.service;

import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public String getMembers() {
        return "Members";
    }
}
