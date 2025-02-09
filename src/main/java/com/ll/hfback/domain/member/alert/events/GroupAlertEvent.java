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
  private final Member targetMember;


  @Override
  public List<Long> getReceiverIds() {
    return switch (alertType) {
      case GROUP_APPLICATION, GROUP_APPLICATION_CANCEL
          -> List.of(chatRoom.getMember().getId());   // 방장에게만
      case GROUP_APPLICATION_REJECTED, GROUP_KICKED_TARGET
          -> List.of(targetMember.getId());  // 신청자에게만
      case GROUP_APPROVED, GROUP_MEMBER_KICKED,
           GROUP_DELETED, GROUP_DELEGATE_OWNER
          -> chatRoom.getJoinMemberIdNickNameList().stream() // 그룹방 모든 참가자에게
              .map(info -> Long.parseLong(info.getFirst()))
              .collect(Collectors.toList());
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
      case GROUP_APPLICATION, GROUP_APPROVED, GROUP_DELEGATE_OWNER,
           GROUP_MEMBER_KICKED, GROUP_APPLICATION_CANCEL
          -> new String[]{chatRoom.getRoomTitle(), targetMember.getNickname()};
      case GROUP_APPLICATION_REJECTED, GROUP_KICKED_TARGET, GROUP_DELETED
          -> new String[]{chatRoom.getRoomTitle()};
    };
  }

}
