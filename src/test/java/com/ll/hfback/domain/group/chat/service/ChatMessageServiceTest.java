package com.ll.hfback.domain.group.chat.service;

import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * packageName    : com.ll.hfback.domain.group.chat.service
 * fileName       : ChatMessageServiceTest
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@SpringBootTest
@ActiveProfiles("test")
class ChatMessageServiceTest {

    @Autowired
    private  ChatMessageRepository chatMessageRepository;
    @Test
    @DisplayName("채팅 메시지 작성 테스트")
    void writeMessage() {
        // given : 테스트 준비 과정
        Long roomId = -1L;
        String testNickname = "test_name";
        String testContent = "test_content";

        // when : 테스트할 기능
        // 채팅 메시지 생성 테스트, fix: 채팅방 기능이 구현되면 roomId 또한 추가해야함
        ChatMessage chatMessage = ChatMessage.builder()
                .nickname(testNickname)
                .chatMessageContent(testContent)
                .build();

        Long testMessageId = chatMessageRepository.save(chatMessage).getChatMessageId();

        // then : 기능이 잘 수행되었는지 확인

        ChatMessage testMessage = chatMessageRepository.findById(testMessageId).get();

        assertThat(testMessage.getNickname()).isEqualTo(testNickname);
        assertThat(testMessage.getChatMessageContent()).isEqualTo(testContent);
    }
}