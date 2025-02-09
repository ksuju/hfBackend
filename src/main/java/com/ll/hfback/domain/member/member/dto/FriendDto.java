package com.ll.hfback.domain.member.member.dto;

import com.ll.hfback.domain.member.member.entity.Friend;
import com.ll.hfback.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class FriendDto {

  private final Long id;  // 친구
  private final String nickname;
  private final String profilePath;
  private final Long requestId;


  public static FriendDto fromAcceptedFriend(Member member) {
    return FriendDto.builder()
        .id(member.getId())
        .nickname(member.getNickname())
        .profilePath(member.getProfilePath())
        .build();
  }

  public static FriendDto fromRequest(Friend friend) {
    return FriendDto.builder()
        .id(friend.getRequester().getId())
        .nickname(friend.getRequester().getNickname())
        .profilePath(friend.getRequester().getProfilePath())
        .requestId(friend.getId())
        .build();
  }
}
