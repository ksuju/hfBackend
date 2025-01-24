package com.ll.hfback.domain.group.chatRoom.repository;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    // 해당 게시글의 모든 모임 조회
    List<ChatRoom> findByFestivalId(String festivalId);
}
