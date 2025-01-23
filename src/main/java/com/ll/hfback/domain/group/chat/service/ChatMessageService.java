package com.ll.hfback.domain.group.chat.service;

import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;

import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.chat.service
 * fileName       : ChatMessageService
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
public interface ChatMessageService {
    // 채팅 메시지 저장
    void writeMessage(Long chatId, ResponseMessage responseMessage);

    List<RequestMessage> readMessages(Long chatId, Long afterChatMessageId);
}
