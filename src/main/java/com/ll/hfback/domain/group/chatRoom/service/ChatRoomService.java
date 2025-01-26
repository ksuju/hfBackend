package com.ll.hfback.domain.group.chatRoom.service;

import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {

    // 해당 게시글의 모든 모임 조회
    List<ChatRoomDto> searchByFestivalId(String festivalId);

    // 해당 게시글의 모임 상세 조회
    Optional<DetailChatRoomDto> searchById(Long id);

    // DB에서 받아온 참여자명단과 대기자명단을 List<String>으로 변환하고, ChatRoom을 ChatRoomDto로 변환하는 메서드
    ChatRoomDto convertToChatRoomDto(ChatRoom chatRoom);

    // DB에서 받아온 참여자명단과 대기자명단을 List<String>으로 변환하고, ChatRoom을 DetailChatRoomDto로 변환하는 메서드
    DetailChatRoomDto convertToDetailChatRoomDto(ChatRoom chatRoom);

    // 해당 게시글에 모임 생성
    void createChatRoom(String festivalId, @Valid CreateChatRoomForm createChatRoomForm);
}
