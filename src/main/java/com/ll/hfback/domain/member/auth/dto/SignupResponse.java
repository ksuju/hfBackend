package com.ll.hfback.domain.member.auth.dto;

import com.ll.hfback.domain.member.member.entity.Member;
import lombok.Getter;

@Getter
public class SignupResponse {
    private final Long id;
    private final String email;
    private final String nickname;
    private final Member.Gender gender;
    private final String phoneNumber;
    private final boolean mkAlarm;
    private final String loginType;

    public SignupResponse(Member member) {
        this.id = member.getId();
        this.email = member.getEmail();
        this.nickname = member.getNickname();
        this.gender = member.getGender();
        this.phoneNumber = member.getPhoneNumber();
        this.mkAlarm = member.isMkAlarm();
        this.loginType = member.getLoginType();
    }

}
