package com.ll.hfback.domain.member.auth.dto;

import com.ll.hfback.domain.member.member.dto.SocialAccountStatus;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.Gender;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Map;


@Builder
public record LoginUserDto(
    @NotNull Long id, @NotNull LocalDateTime createDate, @NotNull String nickname,
    @NotNull String email, LocalDate birthday, String location, Gender gender,
    String phoneNumber, String profilePath, boolean mkAlarm, String loginType,
    Map<String, SocialAccountStatus> socialAccounts, boolean onlySocialAccount
  ) {

  public static LoginUserDto of(Member member) {
    return LoginUserDto.builder().id(member.getId()).createDate(member.getCreateDate())
        .nickname(member.getNickname()).email(member.getEmail()).birthday(member.getBirthday())
        .location(member.getLocation()).gender(member.getGender())
        .phoneNumber(member.getPhoneNumber()).profilePath(member.getProfilePath())
        .mkAlarm(member.isMkAlarm()).loginType(member.getLoginType())
        .socialAccounts(member.getSocialAccountStatuses()).onlySocialAccount(!member.hasPassword()).build();
  }
}
