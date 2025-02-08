package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.member.alert.enums.GroupAlertType;
import com.ll.hfback.domain.member.alert.enums.NavigationType;
import com.ll.hfback.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Getter
@AllArgsConstructor
public class GroupAlertEvent implements AlertEvent {

  private final GroupAlertType alertType;
  private final ChatRoom chatRoom;
  private final Member applyMember;


  @Override
  public List<Long> getReceiverIds() {
    return switch (alertType) {
      case GROUP_APPLICATION ->
          List.of(chatRoom.getMember().getId());   // 방장에게만
      case GROUP_APPLICATION_APPROVED, GROUP_APPLICATION_REJECTED ->
          List.of(applyMember.getId());  // 신청자에게만
      case GROUP_APPROVED, GROUP_NEW_POST ->
          chatRoom.getJoinMemberIdList().stream()  // 모든 참가자에게
              .map(Long::parseLong)
              .collect(Collectors.toList());
      default -> List.of();
    };
  }


  @Override
  public Map<String, Object> getNavigationData() {
    return alertType.getNavigationType() == NavigationType.NONE ?
        Map.of() :
        Map.of("chatRoomId", chatRoom.getId());
  }


  @Override
  public String[] getMessageArgs() {
    return switch (alertType) {
      case GROUP_APPLICATION, GROUP_APPROVED
          -> new String[]{applyMember.getNickname(), chatRoom.getRoomTitle()};
      case GROUP_APPLICATION_APPROVED, GROUP_APPLICATION_REJECTED,
           GROUP_KICKED, GROUP_DELETED, GROUP_NEW_POST
          -> new String[]{chatRoom.getRoomTitle()};
    };
  }
}
