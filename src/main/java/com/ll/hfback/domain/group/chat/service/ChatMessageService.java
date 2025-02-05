package com.ll.hfback.domain.group.chat.service;

import com.ll.hfback.domain.group.chat.response.ResponseMemberStatus;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.request.MessageReadStatusRequest;
import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.rsData.RsData;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
    // 해당 채팅방에 채팅 메시지 저장
    RsData<Object> writeMessage(Long chatRoomId, RequestMessage requestMessage, Member loginUser);

    // 해당 채팅방의 모든 메시지 불러오기
    Page<ResponseMessage> readMessages(Long chatRoomId, int page, Member loginUser);

    // 메시지 불러올 때 사용할 커스텀 페이징
    Pageable customPaging(int page);

    // 조건에 따른 채팅 메시지 검색 기능
    Page<ResponseMessage> searchMessages(Long chatRoomId,
                                         int page,
                                         MessageSearchKeywordsRequest messageSearchKeywordsRequest, Member loginUser);
    
    // 메시지 읽음/안읽음 상태 확인
    void messageReadStatus(Long chatRoomId, MessageReadStatusRequest messageReadStatusRequest, Member loginUser);

    // 채팅방 멤버 로그인/로그아웃 상태 확인
    List<ResponseMemberStatus> memberLoginStatus(Long chatRoomId, Member loginUser);
}
