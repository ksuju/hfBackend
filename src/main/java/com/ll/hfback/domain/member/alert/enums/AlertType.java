package com.ll.hfback.domain.member.alert.enums;

import lombok.Getter;

@Getter
public enum AlertType {
  // 모임 관련
  GROUP_APPLICATION("'%s' 회원님이 '%s' 모임에 참여신청하였습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_APPROVED("'%s' 회원님의 '%s' 모임에 참여하였습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_APPLICATION_APPROVED("회원님의 '%s' 모임 참가 신청이 승인되었습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_APPLICATION_REJECTED("회원님의 '%s' 모임 참가 신청이 거절되었습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_KICKED("회원님이 '%s' 모임에서 강퇴되었습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_DELETED("'%s' 모임이 삭제되었습니다.", NavigationType.GROUP, AlertPriority.NORMAL),
  GROUP_NEW_POST("'%s' 모임에 새 공지사항이 등록되었습니다: %s", NavigationType.GROUP, AlertPriority.NORMAL),

  // 댓글 관련
  COMMENT_REPLY("회원님의 댓글에 새로운 댓글이 달렸습니다: \"%s\"", NavigationType.COMMENT, AlertPriority.NORMAL),

  // 행사 일정 관련
  MEETING_REMINDER_1HOUR("'%s'가 1시간 후에 시작됩니다.", NavigationType.SCHEDULE, AlertPriority.NORMAL),
  MEETING_REMINDER_6HOUR("'%s'가 6시간 후에 시작됩니다.", NavigationType.SCHEDULE, AlertPriority.NORMAL),

  // 회원 관련
  MEMBER_REPORT("회원님이 신고되었습니다.", NavigationType.PROFILE, AlertPriority.NORMAL),
  MEMBER_BLOCK("회원님이 신고 누적으로 차단되었습니다.", NavigationType.PROFILE, AlertPriority.NORMAL),
  PASSWORD_CHANGED("비밀번호가 변경되었습니다.", NavigationType.NONE, AlertPriority.NORMAL),

  // 공지사항 관련
  DD("새로운 전체 공지사항이 등록되었습니다.", NavigationType.POST, AlertPriority.NORMAL),

  // 시스템
  SYSTEM_MAINTENANCE("시스템 점검 안내: %s", NavigationType.NONE, AlertPriority.NORMAL);


  private final String messageTemplate;
  private final NavigationType navigationType;  // 클릭 시 처리 방식
  private final AlertPriority priority;  // 알림 중요도
  public enum AlertPriority {
    EXTREME, HIGH, NORMAL
  }


  AlertType(String messageTemplate, NavigationType navigationType, AlertPriority priority) {
    this.messageTemplate = messageTemplate;
    this.navigationType = navigationType;
    this.priority = priority;
  }

  public String formatMessage(Object... args) {
    return String.format(messageTemplate, args);
  }
}
