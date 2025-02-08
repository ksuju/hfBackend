package com.ll.hfback.domain.member.alert.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertDispatchService {   // 알림 편의용 메서드 묶음 클래스

  private final AlertService alertService;


//  // === 모임 관련 알림 === //
//  @Transactional
//  public void sendGroupApplicationAlert(Long receiverId, String nickname, String roomTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_APPLICATION, navigationData, nickname, roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupApprovedAlert(Long receiverId, String nickname, String roomTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_APPROVED, navigationData, nickname, roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupApplicationApprovedAlert(Long receiverId, String roomTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_APPLICATION_APPROVED, navigationData, roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupApplicationRejectedAlert(Long receiverId, String roomTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_APPLICATION_REJECTED, navigationData, roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupKickedAlert(Long receiverId, String roomTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_KICKED, navigationData, roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupDeletedAlert(Long receiverId, String roomTitle) {
//    alertService.send(receiverId, AlertType.GROUP_DELETED, Map.of(), roomTitle);
//  }
//
//  @Transactional
//  public void sendGroupNewPostAlert(Long receiverId, String roomTitle, String postTitle, Long groupId) {
//    Map<String, Object> navigationData = Map.of(NavigationType.GROUP.getNavigationDataKeys()[0], groupId); // groupId
//    alertService.send(receiverId, AlertType.GROUP_NEW_POST, navigationData, roomTitle, postTitle);
//  }
//
//  // === 댓글 관련 알림 === //
//  @Transactional
//  public void sendCommentReplyAlert(Long receiverId, Long postId, Long commentId, String replyContent) {
//    Map<String, Object> navigationData = Map.of(
//        NavigationType.COMMENT.getNavigationDataKeys()[0], commentId,  // commentId
//        NavigationType.COMMENT.getNavigationDataKeys()[1], postId // postId
//    );
//    alertService.send(receiverId, AlertType.COMMENT_REPLY, navigationData, replyContent);
//  }
//
//  // === 행사 일정 관련 알림 === //
//  @Transactional
//  public void sendMeetingReminderAlert(Long receiverId, String festivalName, Long festivalId, int hours) {
//    Map<String, Object> navigationData = Map.of(NavigationType.FESTIVAL.getNavigationDataKeys()[0], festivalId); // festivalId
//    AlertType type = hours == 1 ? AlertType.MEETING_REMINDER_1HOUR : AlertType.MEETING_REMINDER_6HOUR;
//    alertService.send(receiverId, type, navigationData, festivalName);
//  }

}
