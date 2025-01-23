package com.ll.hfback.domain.group.chat.service;

import com.ll.hfback.domain.group.chat.dto.response.ResponseMessage;

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
    void writeMessage(Long roomId, ResponseMessage responseMessage);
}
