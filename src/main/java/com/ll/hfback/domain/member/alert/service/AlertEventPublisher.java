package com.ll.hfback.domain.member.alert.service;

import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.member.alert.enums.BoardAlertType;
import com.ll.hfback.domain.member.alert.enums.GroupAlertType;
import com.ll.hfback.domain.member.alert.enums.MemberAlertType;
import com.ll.hfback.domain.member.alert.events.BoardAlertEvent;
import com.ll.hfback.domain.member.alert.events.GroupAlertEvent;
import com.ll.hfback.domain.member.alert.events.MemberAlertEvent;
import com.ll.hfback.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertEventPublisher {
  private final ApplicationEventPublisher eventPublisher;


  // === Group ===
  // 모임 신청이 들어옴 (방장)
  public void publishGroupApplication(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPLICATION, chatRoom, applyMember
    ));
  }

  // 모임 신청이 승인됨 (그룹 전체)
  public void publishGroupApproval(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPROVED, chatRoom, applyMember
    ));
  }

  // 모임 신청이 승인됨 (신청자)
  public void publishGroupApplicationApproval(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPLICATION_APPROVED, chatRoom, applyMember
    ));
  }

  // 모임 신청이 거절됨 (신청자)
  public void publishGroupApplicationRejection(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPLICATION_REJECTED, chatRoom, applyMember
    ));
  }

  // 모임에서 강퇴됨 (신청자)
  public void publishGroupKick(ChatRoom chatRoom, Member kickedMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_KICKED, chatRoom, kickedMember
    ));
  }

  // 모임이 삭제됨 (그룹 전체)
  public void publishGroupDeletion(ChatRoom chatRoom, Member member) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_DELETED, chatRoom, member
    ));
  }

  // 새로운 공지사항 등록 (그룹 전체)
  public void publishGroupNewPost(ChatRoom chatRoom, Member member) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_NEW_POST, chatRoom, member
    ));
  }




  // === Festival ===
  // 모임 시작 1시간 전 알림
  public void publishMeetingOneHourBefore() {

  }

  // 모임 시작 6시간 전 알림
  public void publishMeetingSixHoursBefore() {

  }



  // === Comment ===
  // 댓글에 답글이 달림
  public void publishNewReplyOnComment() {

  }




  // === Member ===
  public void publishMemberReport(Long loginUserId) {
    eventPublisher.publishEvent(new MemberAlertEvent(loginUserId, MemberAlertType.MEMBER_REPORT));
  }

  public void publishMemberBlock(Long loginUserId) {
    eventPublisher.publishEvent(new MemberAlertEvent(loginUserId, MemberAlertType.MEMBER_BLOCK));
  }

  public void publishPasswordChange(Long loginUserId) {
    eventPublisher.publishEvent(new MemberAlertEvent(loginUserId, MemberAlertType.PASSWORD_CHANGED));
  }



  // === Board ===
  //
  public void publishNewBoard(Board board, List<Member> activeMembers) {
    eventPublisher.publishEvent(new BoardAlertEvent(
        BoardAlertType.BOARD_NEW, board, activeMembers
    ));
  }

  public void publishSystemMaintenance(Board board, List<Member> activeMembers) {
    eventPublisher.publishEvent(new BoardAlertEvent(
        BoardAlertType.SYSTEM_MAINTENANCE, board, activeMembers
    ));
  }

}
