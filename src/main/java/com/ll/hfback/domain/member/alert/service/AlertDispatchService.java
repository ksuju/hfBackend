package com.ll.hfback.domain.member.alert.service;

import com.ll.hfback.domain.member.alert.enums.AlertType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertDispatchService {   // 알림 편의용 메서드 묶음 클래스

  private final AlertService alertService;


  // === 모임 관련 알림 === //
  // "'%s' 회원님이 '%s' 모임에 참여신청하였습니다."
  @Transactional
  public void sendGroupApplicationAlert(Long receiverId, String applicantName, String groupName, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_APPLICATION, navData, applicantName, groupName);
  }

  // "'%s' 회원님의 '%s' 모임에 참여하였습니다."
  @Transactional
  public void sendGroupApprovedAlert(Long receiverId, String memberName, String groupName, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_APPROVED, navData, memberName, groupName);
  }

  // "회원님의 '%s' 모임 참가 신청이 승인되었습니다."
  @Transactional
  public void sendGroupApplicationApprovedAlert(Long receiverId, String groupName, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_APPLICATION_APPROVED, navData, groupName);
  }

  // "회원님의 '%s' 모임 참가 신청이 거절되었습니다."
  @Transactional
  public void sendGroupApplicationRejectedAlert(Long receiverId, String groupName, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_APPLICATION_REJECTED, navData, groupName);
  }

  // "회원님이 '%s' 모임에서 강퇴되었습니다."
  @Transactional
  public void sendGroupKickedAlert(Long receiverId, String groupName, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_KICKED, navData, groupName);
  }

  // "'%s' 모임이 삭제되었습니다."
  @Transactional
  public void sendGroupDeletedAlert(Long receiverId, String groupName) {
    alertService.send(receiverId, AlertType.GROUP_DELETED, Map.of(), groupName);
  }

  // "'%s' 모임에 새 공지사항이 등록되었습니다: %s"
  @Transactional
  public void sendGroupNewPostAlert(Long receiverId, String groupName, String postTitle, Long groupId) {
    Map<String, Object> navData = Map.of("groupId", groupId);
    alertService.send(receiverId, AlertType.GROUP_NEW_POST, navData, groupName, postTitle);
  }




  // === 댓글 관련 알림 === //
  // "회원님의 댓글에 새로운 댓글이 달렸습니다: \"%s\""
  @Transactional
  public void sendCommentReplyAlert(Long receiverId, Long postId, Long commentId, String replyContent) {
    Map<String, Object> navData = Map.of(
        "postId", postId,
        "commentId", commentId
    );
    alertService.send(receiverId, AlertType.COMMENT_REPLY, navData, replyContent);
  }




  // === 행사 일정 관련 알림 === //
  // "'%s'가 1시간 후에 시작됩니다." or "'%s'가 6시간 후에 시작됩니다."
  @Transactional
  public void sendMeetingReminderAlert(Long receiverId, String meetingTitle, Long meetingId, int hours) {
    Map<String, Object> navData = Map.of("meetingId", meetingId);
    AlertType type = hours == 1 ? AlertType.MEETING_REMINDER_1HOUR : AlertType.MEETING_REMINDER_6HOUR;
    alertService.send(receiverId, type, navData, meetingTitle);
  }




  // === 회원 관련 알림 === //
  // "회원님이 신고되었습니다."
  @Transactional
  public void sendMemberReportAlert(Long receiverId) {
    alertService.send(receiverId, AlertType.MEMBER_REPORT, Map.of());
  }

  // "회원님이 신고 누적으로 차단되었습니다."
  @Transactional
  public void sendMemberBlockAlert(Long receiverId) {
    alertService.send(receiverId, AlertType.MEMBER_BLOCK, Map.of());
  }

  // "비밀번호가 변경되었습니다."
  @Transactional
  public void sendPasswordChangedAlert(Long receiverId) {
    alertService.send(receiverId, AlertType.PASSWORD_CHANGED, Map.of());
  }




  // === 공지사항 관련 알림 === //
  // "새로운 전체 공지사항이 등록되었습니다."
  @Transactional
  public void sendGlobalAnnouncementAlert(Long receiverId) {
    alertService.send(receiverId, AlertType.DD, Map.of());
  }




  // === 시스템 관련 알림 === //
  // "시스템 점검 안내: %s"
  @Transactional
  public void sendSystemMaintenanceAlert(Long receiverId, String message) {
    alertService.send(receiverId, AlertType.SYSTEM_MAINTENANCE, Map.of(), message);
  }

}
