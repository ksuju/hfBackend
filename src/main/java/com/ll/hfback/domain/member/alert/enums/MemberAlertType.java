package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MemberAlertType implements AlertType {

  MEMBER_REPORT(
      "회원 정보\n회원님이 신고되었습니다",
      NavigationType.NONE
  ),
  MEMBER_BLOCK(
      "회원 정보\n신고가 누적되어 계정이 차단되었습니다",
      NavigationType.NONE
  ),
  PASSWORD_CHANGED(
      "회원 정보\n회원님의 비밀번호가 성공적으로 변경되었습니다",
      NavigationType.NONE
  );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.MEMBER;
  }

}
