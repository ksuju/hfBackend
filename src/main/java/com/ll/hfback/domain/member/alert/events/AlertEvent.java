package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.member.alert.enums.AlertType;

import java.util.List;
import java.util.Map;

public interface AlertEvent {
  List<Long> getReceiverIds();
  AlertType getAlertType();
  Map<String, Object> getNavigationData();
  String[] getMessageArgs();
}
