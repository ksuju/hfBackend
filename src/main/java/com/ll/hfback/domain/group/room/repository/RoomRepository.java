package com.ll.hfback.domain.group.room.repository;

import com.ll.hfback.domain.group.room.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.room.repository
 * fileName       : RoomRepository
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
public interface RoomRepository extends JpaRepository<Room, Long> {
    // 해당 게시글의 모든 모임 조회
    List<Room> findByPostFestivalId(String festivalId);
}
