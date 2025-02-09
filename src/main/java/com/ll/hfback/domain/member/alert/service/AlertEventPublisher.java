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

  public void publishGroupApplicationCancel(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPLICATION_CANCEL, chatRoom, applyMember
    ));
  }


  // 모임 신청이 거절됨 (신청자)
  public void publishGroupApplicationRejection(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPLICATION_REJECTED, chatRoom, applyMember
    ));
  }

  // 모임에서 강퇴됨 (신청자)
  public void publishGroupKickToTarget(ChatRoom chatRoom, Member kickedMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_KICKED_TARGET, chatRoom, kickedMember
    ));
  }

  // 모임 신청이 승인됨 (그룹 전체 : 신청자 포함)
  public void publishGroupApproval(ChatRoom chatRoom, Member applyMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_APPROVED, chatRoom, applyMember
    ));
  }

  // 누군가 모임에서 강퇴됨 (그룹 전체)
  public void publishGroupKickToMembers(ChatRoom chatRoom, Member kickedMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_MEMBER_KICKED, chatRoom, kickedMember
    ));
  }

  // 모임장이 위임됨 (그룹 전체)
  public void publishGroupDelegateOwner(ChatRoom chatRoom, Member delegateMember) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_DELEGATE_OWNER, chatRoom, delegateMember
    ));
  }

  // 모임이 삭제됨 (그룹 전체)
  public void publishGroupDeletion(ChatRoom chatRoom) {
    eventPublisher.publishEvent(new GroupAlertEvent(
        GroupAlertType.GROUP_DELETED, chatRoom, null
    ));
  }




  // === Festival ===
  // 모임 시작 1시간 전 알림  (해당 페스티벌의 전체 모임의 전체 참가자)
  public void publishMeetingOneHourBefore() {

  }

  // 모임 시작 6시간 전 알림  (해당 페스티벌의 전체 모임의 전체 참가자)
  public void publishMeetingSixHoursBefore() {

  }



  // === Comment ===
  // 댓글에 답글이 달림  (댓글 주인)
  public void publishNewReplyOnComment() {

  }




  // === Member ===
  // 신고 확정 시 신고 받는 사람에게 가는 알림  (신고 대상)
  public void publishMemberReport(Long reportedId) {
    eventPublisher.publishEvent(new MemberAlertEvent(reportedId, MemberAlertType.MEMBER_REPORT));
  }

  // 회원 차단 시 차단당하는 회원에게 가는 알림  (신고 대상)
  public void publishMemberBlock(Long reportedId) {
    eventPublisher.publishEvent(new MemberAlertEvent(reportedId, MemberAlertType.MEMBER_BLOCK));
  }

  // 비밀번호 변경 시 알림  (본인)
  public void publishPasswordChange(Long loginUserId) {
    eventPublisher.publishEvent(new MemberAlertEvent(loginUserId, MemberAlertType.PASSWORD_CHANGED));
  }



  // === Board ===
  // 새로운 공지사항 등록됨  (전체 회원 (차단, 탈퇴 회원 제외))
  public void publishNewBoard(Board board, List<Member> activeMembers) {
    eventPublisher.publishEvent(new BoardAlertEvent(
        BoardAlertType.BOARD_NEW, board, activeMembers
    ));
  }

  // 시스템 점검 안내  (아직 미사용)
  public void publishSystemMaintenance(Board board, List<Member> activeMembers) {
    eventPublisher.publishEvent(new BoardAlertEvent(
        BoardAlertType.SYSTEM_MAINTENANCE, board, activeMembers
    ));
  }

}
