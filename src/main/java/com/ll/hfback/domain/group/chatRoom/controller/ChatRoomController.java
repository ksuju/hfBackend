package com.ll.hfback.domain.group.chatRoom.controller;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService roomService;

    // 해당 게시글의 모든 모임 조회
    @GetMapping("/{festival-id}/chatRooms")
    public List<ChatRoom> getRooms(@PathVariable("festival-id") String festivalId) {
        List<ChatRoom> chatRooms = roomService.searchByFestivalId(festivalId);

        return chatRooms;
    }

    // 해당 게시글의 모임 상세 조회
    @GetMapping("/chat-room/{chat-room-id}")
    public Optional<ChatRoom> getRoom(@PathVariable("chat-room-id") Long chatRoomId) {
        Optional<ChatRoom> chatRoom = roomService.searchById(chatRoomId);

        return chatRoom;
    }

    // 해당 게시글에 모임 생성
//  @PostMapping("/room/{festivalId}")


}
