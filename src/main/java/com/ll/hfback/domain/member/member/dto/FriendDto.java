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


  public static FriendDto fromAcceptedFriend(Friend friend, Long loginUserId) {
    Member friendMember = friend.getRequester().getId().equals(loginUserId)
        ? friend.getReceiver()
        : friend.getRequester();

    return FriendDto.builder()
        .id(friendMember.getId())
        .nickname(friendMember.getNickname())
        .profilePath(friendMember.getProfilePath())
        .requestId(friend.getId())
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
