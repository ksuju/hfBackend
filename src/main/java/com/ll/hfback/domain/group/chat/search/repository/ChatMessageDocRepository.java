package com.ll.hfback.domain.group.chat.search.repository;

import com.ll.hfback.domain.group.chat.search.document.ChatMessageDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * packageName    : com.ll.hfback.domain.group.chat.search.repository
 * fileName       : ChatMessageDocRepository
 * author         : sungjun
 * date           : 2025-02-10
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-10        kyd54       최초 생성
 */
public interface ChatMessageDocRepository extends ElasticsearchRepository<ChatMessageDoc, Long> {
    Page<ChatMessageDoc> findByChatRoomIdAndChatMessageContentContaining(Long chatRoomId, String keyword, Pageable pageable);

    Page<ChatMessageDoc> findByChatRoomIdAndNickname(Long chatRoomId,String nickname, Pageable pageable);

    Page<ChatMessageDoc> findByChatRoomId(Long chatRoomId, Pageable pageable);
}
