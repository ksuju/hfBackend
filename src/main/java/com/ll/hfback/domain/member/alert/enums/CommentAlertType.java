package com.ll.hfback.domain.member.alert.enums;

import com.ll.hfback.domain.member.alert.entity.Alert.AlertDomain;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CommentAlertType implements AlertType {

  COMMENT_REPLY(
      "'%s' 님이 회원님의 댓글에 답글을 남겼습니다", NavigationType.COMMENT
  );



  private final String messageTemplate;
  private final NavigationType navigationType;

  @Override
  public AlertDomain getDomain() {
    return AlertDomain.COMMENT;
  }
}
