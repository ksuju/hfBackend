package com.ll.hfback.domain.group.chat.serviceImpl;

import com.ll.hfback.domain.group.chat.dto.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.entity.Chat;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.repository.ChatRepository;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.group.room.repository.RoomRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private final RoomRepository roomRepository;
    private final ChatRepository chatRepository;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class.getName());

    @Transactional
    public void writeMessage(Long roomId, ResponseMessage responseMessage) {
        logger.info("채팅 메시지 작성");
        try {
            // roomId로 모임방 정보 가져오기
            Room room = roomRepository.findById(roomId).orElse(null);

            if(room != null) {
                // roomId로 채팅방 정보 가져와야함
                Chat chat = chatRepository.findById(room.getChat().getId()).orElse(null);

                // memberId로 멤버 정보 가져오기
                Member member = memberRepository.findById(responseMessage.getMemberId()).orElse(null);

                // 채팅 메시지 저장
                ChatMessage chatMessage = ChatMessage.builder()
                        .chat(chat)
                        .chatMessageContent(responseMessage.getContent())
                        .build();

                // Room 엔티티의 chatMessages 리스트에 추가
                if(chat != null) {
                    chat.getChatMessages().add(chatMessage);
                }
                
                chatMessageRepository.save(chatMessage);
                logger.info("채팅 메시지 작성 완료");
            } else {
                logger.error("채팅 메시지 작성 실패, Room or Chat is null");
            }

        } catch (Exception E) { // fix: 어떤 에러가 발생 할 수 있는지, 에러 유형별 처리 방법 생각 (던지기 x)
            logger.error("채팅 메시지 작성 실패 : " + E);
        }
    }

}
