package com.ll.hfback.domain.group.chat.repository;

import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.response.ResponseMessageCount;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.chat.repository
 * fileName       : ChatRepository
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long>, QuerydslPredicateExecutor<ChatMessage> {
    Page<ChatMessage> findByChatRoomId(Long chatRoomId, Pageable pageable);

    @Query("""
    SELECT new com.ll.hfback.domain.group.chat.response.ResponseMessageCount(m.id, COUNT(cru.member.id))
    FROM ChatMessage m
    LEFT JOIN ChatRoomUser cru
    ON m.chatRoom.id = cru.chatRoom.id
    AND m.id > cru.lastReadMessageId
    WHERE m.chatRoom.id = :chatRoomId
    GROUP BY m.id
""")
    List<ResponseMessageCount> getUnreadMessageCount(@Param("chatRoomId") Long chatRoomId);

    // 채팅방 삭제 시 해당 채팅방의 모든 메시지 삭제(FK 제약)
    void removeAllByChatRoomId(Long chatRoomId);

    ChatMessage findByChatMessageContent(String content);
}
