package com.ll.hfback.domain.group.chat.controller;

import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.chat.controller
 * fileName       : ChatController
 * author         : sungjun
 * date           : 2025-01-21
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-21        kyd54       최초 생성
 */
@RestController
@RequestMapping("/api/v1/groups/{chat-room-id}")
@RequiredArgsConstructor
public class ChatMessageController {
    private final ChatMessageService chatMessageService;

    @PostMapping("/messages") // 채팅 메시지 작성
    public void writeMessage(@PathVariable("chat-room-id") Long chatRoomId,
                             @RequestBody ResponseMessage responseMessage) {
        chatMessageService.writeMessage(chatRoomId,
                responseMessage);
    }

    @GetMapping("/messages")
    public List<ChatMessage> readMessages(@PathVariable("chat-room-id") Long chatRoomId) {

        return chatMessageService.readMessages(chatRoomId);
    }
}
