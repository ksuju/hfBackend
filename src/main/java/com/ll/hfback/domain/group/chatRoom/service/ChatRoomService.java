package com.ll.hfback.domain.group.chatRoom.service;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomRepository roomRepository;

    @Transactional(readOnly = true)
    // 해당 게시글의 모든 모임 조회
    public List<ChatRoom> searchByFestivalId(String festivalId) {
        return roomRepository.findByFestivalId(festivalId);
    }

    @Transactional(readOnly = true)
    // 해당 게시글의 모임 상세 조회
    public Optional<ChatRoom> searchById(Long id) {
        return roomRepository.findById(id);
    }

    @Transactional
    // 해당 게시글에 모임 생성
    public void createRoom(int roomMemberLimit) {
        if (roomMemberLimit > 100) {
            throw new IllegalArgumentException("roomMemberLimit는 100을 초과할 수 없습니다.");
        }
    }
}
