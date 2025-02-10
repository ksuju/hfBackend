package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.member.alert.enums.FriendAlertType;
import com.ll.hfback.domain.member.alert.enums.NavigationType;
import com.ll.hfback.domain.member.member.entity.Friend;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class FriendAlertEvent implements AlertEvent {

  private final Friend friend;
  private final FriendAlertType alertType;


  @Override
  public List<Long> getReceiverIds() {
    return switch (alertType) {
      case FRIEND_APPLICATION, FRIEND_APPLICATION_CANCEL
          -> List.of(friend.getReceiver().getId());   // 방장에게만
      case FRIEND_APPLICATION_ACCEPT, FRIEND_APPLICATION_REJECT
          -> List.of(friend.getRequester().getId());  // 신청자에게만
      case FRIEND_DISCONNECT
          -> List.of(friend.getRequester().getId(), friend.getReceiver().getId());  // 양쪽에게
    };
  }


  @Override
  public Map<String, Object> getNavigationData() {
    return alertType.getNavigationType() == NavigationType.NONE ?
        Map.of() :
        Map.of(
            "requestId", friend.getId(),
            "success", "/friend-requests/{requestId}/accept",
            "failure", "/friend-requests/{requestId}/reject"
        );
  }


  @Override
  public String[] getMessageArgs() {
    return switch (alertType) {
      case FRIEND_APPLICATION, FRIEND_APPLICATION_CANCEL
          -> new String[]{friend.getRequester().getNickname()};
      case FRIEND_APPLICATION_ACCEPT, FRIEND_APPLICATION_REJECT
          -> new String[]{friend.getReceiver().getNickname()};
      case FRIEND_DISCONNECT
          -> new String[]{friend.getRequester().getNickname(), friend.getReceiver().getNickname()};
    };
  }

}
