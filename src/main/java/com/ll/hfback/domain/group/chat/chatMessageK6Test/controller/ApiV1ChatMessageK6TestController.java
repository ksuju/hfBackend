package com.ll.hfback.domain.group.chat.chatMessageK6Test.controller;

import com.ll.hfback.domain.group.chat.chatMessageK6Test.service.ChatMessageK6TestService;
import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.search.service.ChatMessageDocService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.ll.hfback.domain.group.chat.chatMessageK6Test.controller
 * fileName       : ApiV1ChatMessageK6TestController
 * author         : sungjun
 * date           : 2025-02-11
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-11        kyd54       최초 생성
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chatRooms/{chatRoom-id}/messages/search")
public class ApiV1ChatMessageK6TestController {

    private final ChatMessageK6TestService chatMessageK6TestService;
    // 엘라스틱서치 k6 테스트
    @GetMapping("/eks/test")
    public RsData<Page<ResponseMessage>> searchMessagesEks (@PathVariable("chatRoom-id") Long chatRoomId,
                                                         @RequestParam(value = "page", defaultValue = "0") int page,
                                                         @ModelAttribute MessageSearchKeywordsRequest messageSearchKeywordsRequest) {
        try {
            Page<ResponseMessage> messages = chatMessageK6TestService.searchMessagesEks(chatRoomId, page, messageSearchKeywordsRequest);
            return new RsData<>("200", "검색 조건에 따른 채팅 메시지 조회에 성공했습니다.", messages);
        } catch (Exception e) {
            return new RsData<>("500", "엘라스틱서치 채팅방 메시지 검색 실패");
        }
    }


    // mysql 검색 k6 테스트
    @GetMapping("/test")
    public RsData<Page<ResponseMessage>> searchMessagesMysql(@PathVariable("chatRoom-id") Long chatRoomId,
                                                        @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @ModelAttribute MessageSearchKeywordsRequest messageSearchKeywordsRequest) {
        try {
            Page<ResponseMessage> messages = chatMessageK6TestService.searchMessagesMysql(chatRoomId, page, messageSearchKeywordsRequest);
            messages.getContent().forEach(message -> {
            });
            return new RsData<>("200", "검색 조건에 따른 채팅 메시지 조회에 성공했습니다.", messages);
        } catch (Exception e) {
            return new RsData<>("500", "검색 조건에 따른 채팅 메시지 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }
}
