package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum GroupAlertType implements AlertType {

  // 대상 : 그룹 방장
  GROUP_APPLICATION(
      "%s\n'%s' 님이 참가 신청을 했습니다",
      NavigationType.GROUP
  ),
  GROUP_APPLICATION_CANCEL(
      "%s\n'%s' 님이 참가 신청을 취소했습니다",
      NavigationType.GROUP
  ),


  // 대상 : 신청자
  GROUP_APPLICATION_REJECTED(
      "%s\n회원님의 참가 신청이 거절되었습니다",
      NavigationType.GROUP
  ),
  GROUP_KICKED_TARGET(
      "%s\n회원님이 강퇴되었습니다",
      NavigationType.GROUP),


  // 대상 : 그룹 멤버 전체
  GROUP_APPROVED(
      "%s\n'%s' 님이 참여했습니다",
      NavigationType.GROUP
  ),
  GROUP_MEMBER_KICKED(
      "%s\n'%s' 님이 강퇴되었습니다",
      NavigationType.GROUP),
  GROUP_DELEGATE_OWNER(
      "%s\n'%s' 님이 모임장을 위임받았습니다",
      NavigationType.GROUP),
  GROUP_DELETED(
      "%s\n해당 모임이 삭제되었습니다",
      NavigationType.NONE
  );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.GROUP;
  }

}
