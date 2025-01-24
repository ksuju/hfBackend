package com.ll.hfback.domain.group.chat.serviceImpl;

import com.ll.hfback.domain.group.chat.dto.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class.getName());
    private final ChatRoomRepository chatRoomRepository;

    @Transactional
    public void writeMessage(Long chatId, ResponseMessage responseMessage) {
        logger.info("채팅 메시지 작성");
        try {
            // 빈 메시지 또는 250자 초과 메시지 검사
            if (responseMessage.getContent() == null || responseMessage.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("채팅 메시지는 비어 있을 수 없습니다.");
            }
            if (responseMessage.getContent().length() > 250) {
                throw new IllegalArgumentException("채팅 메시지는 250자를 넘을 수 없습니다.");
            }

            // chatId로 채팅방 정보 가져오기
            ChatRoom chatRoom = chatRoomRepository.findById(chatId).orElse(null);

            if(chatRoom != null) {
                // memberId로 멤버 정보 가져오기
                Member member = memberRepository.findById(responseMessage.getMemberId()).orElse(null);

                // 채팅 메시지 저장
                ChatMessage chatMessage = ChatMessage.builder()
                        .chatRoom(chatRoom)
                        .nickname(member.getNickname())
                        .chatMessageContent(responseMessage.getContent())
                        .build();

                chatMessageRepository.save(chatMessage);
                logger.info("채팅 메시지 작성 완료");
            } else {
                logger.error("채팅 메시지 작성 실패, Room or Chat is null");
            }
            // fix: 어떤 에러가 발생 할 수 있는지, 에러 유형별 처리 방법 생각 (던지기 x)
            // 예외를 발생시켜 클라이언트에 알림, 필요하면 400 Bad Request와 같은 HTTP 응답 코드 설정
            // 또는 커스텀 예외를 던질 수 있음
        } catch (IllegalArgumentException e) {
            logger.error("채팅 메시지 작성 실패: " + e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("채팅 메시지 작성 실패 : " + e);
            throw e;
        }
    }

    @Transactional(readOnly = true)
    public List<ChatMessage> readMessages(Long chatRoomId) {
        return chatMessageRepository.findByChatRoomId(chatRoomId);
    }
}
