package com.ll.hfback.domain.member.auth.dto;

import com.ll.hfback.domain.group.chatRoom.converter.StringListConverter;
import com.ll.hfback.domain.member.member.dto.SocialAccountStatus;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.MemberRole;
import com.ll.hfback.domain.member.member.entity.Member.Gender;
import jakarta.persistence.Convert;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


@Builder
public record LoginUserDto(
        @NotNull Long id, @NotNull LocalDateTime createDate, @NotNull String nickname,
        @Convert(converter = StringListConverter.class)List<String> joinRoomIdList, @Convert(converter = StringListConverter.class)List<String> waitRoomIdList,
        @NotNull String email, LocalDate birthday, String location, Gender gender,
        String phoneNumber, String profilePath, boolean mkAlarm, String loginType, MemberRole role,
        Map<String, SocialAccountStatus> socialAccounts, boolean onlySocialAccount
  ) {

  public static LoginUserDto of(Member member) {
    return LoginUserDto.builder().id(member.getId()).createDate(member.getCreateDate())
        .nickname(member.getNickname()).joinRoomIdList(member.getJoinRoomIdList()).waitRoomIdList(member.getWaitRoomIdList())
        .email(member.getEmail()).birthday(member.getBirthday())
        .location(member.getLocation()).gender(member.getGender())
        .phoneNumber(member.getPhoneNumber()).profilePath(member.getProfilePath())
        .mkAlarm(member.isMkAlarm()).loginType(member.getLoginType()).role(member.getRole())
        .socialAccounts(member.getSocialAccountStatuses()).onlySocialAccount(!member.hasPassword()).build();
  }
}
