package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupAlertType implements AlertType {

  GROUP_APPLICATION(
      "'%s' 님이 '%s' 모임에 참가 신청을 했습니다",
      NavigationType.GROUP
  ),
  GROUP_APPROVED(
      "'%s' 님이 '%s' 모임에 참여했습니다",
      NavigationType.GROUP
  ),
  GROUP_APPLICATION_APPROVED(
      "회원님의 '%s' 모임 참가 신청이 승인되었습니다",
      NavigationType.GROUP
  ),
  GROUP_APPLICATION_REJECTED(
      "회원님의 '%s' 모임 참가 신청이 거절되었습니다",
      NavigationType.GROUP
  ),
  GROUP_KICKED(
      "회원님이 '%s' 모임에서 강퇴되었습니다",
      NavigationType.GROUP),
  GROUP_DELETED(
      "'%s' 모임이 삭제되었습니다",
      NavigationType.NONE
  ),
  GROUP_NEW_POST(
      "'%s' 모임에 새로운 공지가 등록되었습니다",
      NavigationType.GROUP
  );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.GROUP;
  }

}
