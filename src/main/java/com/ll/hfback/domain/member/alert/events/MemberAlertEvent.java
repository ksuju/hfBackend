package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.member.alert.enums.MemberAlertType;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class MemberAlertEvent implements AlertEvent {

  private final Long LoginUserId;
  private final MemberAlertType alertType;


  @Override
  public List<Long> getReceiverIds() {
    return List.of(LoginUserId);
  }

  @Override
  public Map<String, Object> getNavigationData() {
    return Map.of();
  }

  @Override
  public String[] getMessageArgs() {
    return new String[]{};
  }
}
