package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FestivalAlertType implements AlertType {

  FESTIVAL_ONGOING(
      "%s 페스티벌이 현재 진행중입니다!",
      NavigationType.FESTIVAL
  );

  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.FESTIVAL;
  }
}
