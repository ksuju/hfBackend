package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;

public interface AlertType {

  String getMessageTemplate();
  NavigationType getNavigationType();
  AlertDomain getDomain();
  String name();


  default String formatMessage(Object... args) {
    return String.format(getMessageTemplate(), args);
  }
}
