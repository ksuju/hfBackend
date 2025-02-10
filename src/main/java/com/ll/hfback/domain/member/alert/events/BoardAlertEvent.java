package com.ll.hfback.domain.member.alert.events;

import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.member.alert.enums.BoardAlertType;
import com.ll.hfback.domain.member.alert.enums.NavigationType;
import com.ll.hfback.domain.member.member.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class BoardAlertEvent implements AlertEvent {

  private final BoardAlertType alertType;
  private final Board board;
  private final List<Member> activeMembers;


  @Override
  public List<Long> getReceiverIds() {
    return activeMembers.stream()
        .map(Member::getId)
        .toList();
  }

  @Override
  public Map<String, Object> getNavigationData() {
    return alertType.getNavigationType() == NavigationType.NONE ?
        Map.of() :
        Map.of("boardId", board.getId());
  }

  @Override
  public String[] getMessageArgs() {
    return new String[]{};
  }
}
