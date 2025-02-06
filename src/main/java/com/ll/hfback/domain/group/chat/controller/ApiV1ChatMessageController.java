package com.ll.hfback.domain.group.chat.controller;

import com.ll.hfback.domain.group.chat.response.ResponseMemberStatus;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.request.MessageReadStatusRequest;
import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.group.chat.response.ResponseMessageCount;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
@RequestMapping("/api/v1/chatRooms/{chatRoom-id}")
@RequiredArgsConstructor
public class ApiV1ChatMessageController {
    private final ChatMessageService chatMessageService;

    // 채팅 메시지 작성
    @PostMapping("/messages")
    public RsData<Object> writeMessage(@PathVariable("chatRoom-id") Long chatRoomId,
                                       @RequestBody RequestMessage requestMessage, @LoginUser Member loginUser) {
        try {
            return chatMessageService.writeMessage(chatRoomId, requestMessage, loginUser); // ✅ 서비스에서 반환한 응답을 그대로 반환
        } catch (Exception e) {
            return new RsData<>("500", "채팅 메시지 작성 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 채팅 메시지 조회
    @GetMapping("/messages")
    public RsData<Page<ResponseMessage>> readMessages(@PathVariable("chatRoom-id") Long chatRoomId,
                                                @RequestParam(value = "page", defaultValue = "0") int page, @LoginUser Member loginUser) {
        try {
            Page<ResponseMessage> messages = chatMessageService.readMessages(chatRoomId, page, loginUser);
            return new RsData<>("200", "채팅 메시지 조회 성공", messages);
        } catch (Exception e) {
            return new RsData<>("500", "채팅 메시지 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 채팅 메시지 읽음 카운트
    @GetMapping("/messages/count")
    public RsData<List<ResponseMessageCount>> messageCount(@PathVariable("chatRoom-id") Long chatRoomId,
                                                    @LoginUser Member loginUser) {
        try {
            return new RsData<>("200", "메시지 카운트 불러오기 성공", chatMessageService.messageCount(chatRoomId, loginUser));
        } catch (Exception e) {
            return new RsData<>("500", "메시지 카운트 불러오기 실패: " + e.getMessage());
        }
    }

    // 조건에 따른 채팅 메시지 검색 기능
    @GetMapping("/messages/search")
    public RsData<Page<ResponseMessage>> searchMessages(@PathVariable("chatRoom-id") Long chatRoomId,
                                                @RequestParam(value = "page", defaultValue = "0") int page,
                                                        @ModelAttribute MessageSearchKeywordsRequest messageSearchKeywordsRequest, @LoginUser Member loginUser) {
        try {
            Page<ResponseMessage> messages = chatMessageService.searchMessages(chatRoomId, page, messageSearchKeywordsRequest, loginUser);
            return new RsData<>("200", "검색 조건에 따른 채팅 메시지 조회에 성공했습니다.", messages);
        } catch (Exception e) {
            return new RsData<>("500", "검색 조건에 따른 채팅 메시지 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    // 메시지 읽음/안읽음 상태 확인용 필드 수정
    @PutMapping("/messages/readStatus")
    public RsData<Void> messageReadStatus(@PathVariable("chatRoom-id") Long chatRoomId,
                                  @RequestBody MessageReadStatusRequest messageReadStatusRequest, @LoginUser Member loginUser) {
        try {
            chatMessageService.messageReadStatus(chatRoomId, messageReadStatusRequest, loginUser);
            return new RsData<>("200", "메시지 수신 상태 변경에 성공했습니다.");
        } catch (Exception e) {
            return new RsData<>("500", "메시지 수신 상태 변경에 실패했습니다." + e.getMessage());
        }
    }

    // 채팅방 멤버 로그인/로그아웃 상태 확인
    @GetMapping("/members")
    public RsData<List<ResponseMemberStatus>> memberLoginStatus(@PathVariable("chatRoom-id") Long chatRoomId, @LoginUser Member loginUser) {
        try {
            return new RsData<List<ResponseMemberStatus>>("200", "채팅방 멤버 로그인 상태 조회 성공", chatMessageService.memberLoginStatus(chatRoomId, loginUser));
        } catch (Exception e) {
            return new RsData<>("500", "채팅방 멤버 로그인 상태 조회 실패: " + e.getMessage());
        }
    }

    // 채팅방 멤버 로그인 상태 변경 (로그아웃)
    @PatchMapping("/members/logout")
    public RsData<Void> chatMemberLogout(@PathVariable("chatRoom-id") Long chatRoomId,
                                         @LoginUser Member loginUser) {
        try {
            chatMessageService.chatMemberLogout(chatRoomId, loginUser);
            return new RsData<Void>("200", "채팅방 멤버 로그아웃 처리 성공");
        } catch (Exception e) {
            return new RsData<Void>("500", "채팅방 멤버 로그아웃 처리 실패" + e);
        }
    }

    // 채팅방 멤버 로그인 상태 변경 (로그인)
    @PatchMapping("/members/login")
    public RsData<Void> chatMemberLogin(@PathVariable("chatRoom-id") Long chatRoomId,
                                         @LoginUser Member loginUser) {
        try {
            chatMessageService.chatMemberLogin(chatRoomId, loginUser);
            return new RsData<Void>("200", "채팅방 멤버 로그인 처리 성공");
        } catch (Exception e) {
            return new RsData<Void>("500", "채팅방 멤버 로그인 처리 실패" + e);
        }
    }
}
