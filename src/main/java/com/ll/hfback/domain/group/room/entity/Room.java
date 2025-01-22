package com.ll.hfback.domain.group.room.entity;

import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.room.entity
 * fileName       : Group
 * author         : sungjun
 * date           : 2025-01-21
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-21        kyd54       최초 생성
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomId;

    // FK
    private Long festivalId;
    private Long memberId;
    //

    private String roomContent;
    private int roomMemberLimit;
    private int roomState;

    @OneToMany
    private List<ChatMessage> chatMessages; // 그룹 내 모든 메시지 저장
}
