package com.ll.hfback.domain.group.chatRoom.repository;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 해당 게시글의 모든 모임 조회
    Page<ChatRoom> findByFestivalId(String festivalId, Pageable pageable);
}
