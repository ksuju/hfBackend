package com.ll.hfback.domain.group.chat.serviceImpl;

import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.group.room.repository.RoomRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

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
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final RoomRepository roomRepository;
    private final MemberRepository memberRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class.getName());

    public void writeMessage(Long roomId, Long memberId, String content) {
        logger.info("채팅 메시지 작성");
        try {
            // roomId로 채팅방 정보 가져오기
            Room room = roomRepository.findById(roomId).get();

            // memberId로 멤버 정보 가져오기
            Member member = memberRepository.findById(memberId).get();
            
            // 채팅 메시지 저장
            ChatMessage chatMessage = ChatMessage.builder()
                    .room(room)
                    .member(member)
                    .chatMessageContent(content)
                    .build();
            chatMessageRepository.save(chatMessage);
            logger.info("채팅 메시지 작성 완료");
        } catch (Exception E) { // fix: 어떤 에러가 발생 할 수 있는지, 에러 유형별 처리 방법 생각 (던지기 x)
            logger.error("채팅 메시지 작성 실패 : " + E);
        }
    }

}
