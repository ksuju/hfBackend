package com.ll.hfback.domain.group.chatRoom.service;

import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ChatRoomService {
    // 모든 모임채팅방 조회
    Page<DetailChatRoomDto> findAll(Pageable pageable);

    // 모임채팅방 검색
    Page<DetailChatRoomDto> searchByKeyword(String keyword, Pageable pageable);

    // 해당 게시글의 모든 모임채팅방 조회
    Page<DetailChatRoomDto> searchByFestivalId(String festivalId, Pageable pageable);

    // 해당 게시글에 모임채팅방 생성
    void createChatRoom(String festivalId, @Valid CreateChatRoomForm createChatRoomForm, Member loginUser);

    // 해당 모임채팅방 수정(방장만 가능)
    void updateChatRoom(String chatRoomId, @Valid UpdateChatRoomForm updateChatRoomForm, Member loginUser);

    // 해당 모임채팅방 삭제
    void deleteChatRoom(String chatRoomId, Member loginUser);

    // 해당 모임채팅방에 참여신청
    void applyChatRoom(String chatRoomId, Member loginUser);

    // 해당 모임채팅방에 참여신청 취소
    void cancelApplyChatRoom(String chatRoomId, Member loginUser);

    // 해당 모임채팅방 참여신청 승인
    void approveApplyChatRoom(String chatRoomId, String applyMemberId, Member loginUser);

    // 해당 모임채팅방 참여신청 거절
    void refuseApplyChatRoom(String chatRoomId, String applyMemberId, Member loginUser);

    // 해당 모임채팅방의 참여자 강퇴
    void unqualifyChatRoom(String chatRoomId, String unqualifyMemberId, Member loginUser);

    // 해당 모임채팅방 나가기(방장이 나가는 경우 해당 모임채팅방 삭제)
    void leaveChatRoom(String chatRoomId, Member loginUser);

    // 해당 모임채팅방에서 참여자에게 방장권한 위임
    void delegateChatRoom(String chatRoomId, String delegateMemberId, Member loginUser);
}
