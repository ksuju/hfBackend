package com.ll.hfback.domain.group.chatRoom.controller;

import com.ll.hfback.domain.group.chatRoom.dto.ChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.dto.DetailChatRoomDto;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    // 해당 게시글의 모든 모임채팅방 조회
    @GetMapping("/{festival-id}/chat-rooms")
    public List<ChatRoomDto> getRooms(@PathVariable("festival-id") String festivalId) {
        return chatRoomService.searchByFestivalId(festivalId);
    }

    // 해당 게시글의 모임채팅방 상세 조회
    @GetMapping("/chat-room/{chat-room-id}")
    public Optional<DetailChatRoomDto> getRoom(@PathVariable("chat-room-id") Long chatRoomId) {
        return chatRoomService.searchById(chatRoomId);
    }

    // 해당 게시글에 모임채팅방 생성
    @PostMapping("/{festival-id}/chat-rooms")
    public ResponseEntity<String> createRoom(@PathVariable("festival-id") String festivalId, @RequestBody @Valid CreateChatRoomForm createChatRoomForm) {
        chatRoomService.createChatRoom(festivalId, createChatRoomForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("모임이 성공적으로 만들어졌습니다.");
    }

}
