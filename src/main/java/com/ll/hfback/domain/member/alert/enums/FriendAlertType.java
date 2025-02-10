package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FriendAlertType implements AlertType {

  // 대상 : 친구 요청 수신자
  FRIEND_APPLICATION(
      "친구 신청 알림\n'%s' 님으로부터 친구 신청이 왔습니다",
      NavigationType.SELECT
  ),
  FRIEND_APPLICATION_CANCEL(
      "친구 신청 취소 알림\n'%s' 님이 친구 신청을 취소했습니다",
      NavigationType.SELECT
  ),


  // 대상 : 친구 요청 발신자
  FRIEND_APPLICATION_ACCEPT(
      "친구 수락 알림\n'%s' 님이 친구 신청을 수락했습니다",
      NavigationType.NONE
  ),
  FRIEND_APPLICATION_REJECT(
      "친구 거절 알림\n'%s' 님이 친구 신청을 거절했습니다",
      NavigationType.NONE
  ),


  // 대상 : 양쪽
  FRIEND_DISCONNECT(
      "친구 삭제 알림\n'%s' 님과 '%s' 님의 친구 관계가 해제되었습니다",
      NavigationType.NONE
  );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.FRIEND;
  }

}
