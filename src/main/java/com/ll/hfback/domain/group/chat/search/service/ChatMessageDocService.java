package com.ll.hfback.domain.group.chat.search.service;

import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.member.member.entity.Member;
import org.springframework.data.domain.Page;

/**
 * packageName    : com.ll.hfback.domain.group.chat.search.service
 * fileName       : ChatMessageDocService
 * author         : sungjun
 * date           : 2025-02-10
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-10        kyd54       최초 생성
 */
public interface ChatMessageDocService {

    // 조건에 따른 채팅 메시지 검색 기능
    Page<ResponseMessage> searchMessages(Long chatRoomId,
                                         int page,
                                         MessageSearchKeywordsRequest messageSearchKeywordsRequest, Member loginUser);
}
