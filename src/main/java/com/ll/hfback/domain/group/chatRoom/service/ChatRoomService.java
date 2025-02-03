package com.ll.hfback.domain.group.chatRoom.service;

import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    // 모든 모임채팅방 조회
    Page<ChatRoomDto> findAll(Pageable pageable);

    // 해당 게시글의 모든 모임채팅방 조회
    Page<ChatRoomDto> searchByFestivalId(String festivalId, Pageable pageable);

    // 해당 게시글의 모임채팅방 상세 조회(참여자 명단에 있는 사용자만 접근 가능)
    Optional<DetailChatRoomDto> searchById(Long id, Member loginUser);

    // 해당 게시글에 모임채팅방 생성
    void createChatRoom(String festivalId, @Valid CreateChatRoomForm createChatRoomForm, Member loginUser);

    // 해당 모임채팅방 수정(방장만 가능)
    void updateChatRoom(Long chatRoomId, @Valid UpdateChatRoomForm updateChatRoomForm, Member loginUser);

    // 해당 모임채팅방 삭제
    void deleteChatRoom(Long chatRoomId, Member loginUser);

    // 해당 모임채팅방에 참여신청
    void applyChatRoom(Long chatRoomId, Member loginUser);

    // 해당 모임채팅방에 참여신청 취소
    void cancelApplyChatRoom(Long chatRoomId, Member loginUser);

    // 해당 모임채팅방 참여신청 승인
    void approveApplyChatRoom(Long chatRoomId, String applyMemberId, Member loginUser);

    // 해당 모임채팅방 참여신청 거절
    void refuseApplyChatRoom(Long chatRoomId, String applyMemberId, Member loginUser);

    // 해당 모임채팅방의 참여자 강퇴
    void unqualifyChatRoom(Long chatRoomId, String memberId, Member loginUser);

    // 해당 모임채팅방 나가기(방장이 나가는 경우 해당 모임채팅방 삭제)
    void leaveChatRoom(Long chatRoomId, Member loginUser);

    // 해당 모임채팅방에서 참여자에게 방장권한 위임
    void delegateChatRoom(Long chatRoomId, Long memberId, Member loginUser);
}
