package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FestivalAlertType implements AlertType {

  MEETING_REMINDER_1HOUR(
      "'%s'가 1시간 후 시작됩니다",
      NavigationType.FESTIVAL
  ),
  MEETING_REMINDER_6HOUR(
      "'%s'가 6시간 후 시작됩니다",
      NavigationType.FESTIVAL
      );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.FESTIVAL;
  }
}
