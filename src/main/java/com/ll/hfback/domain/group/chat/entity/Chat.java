package com.ll.hfback.domain.group.chat.entity;

import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.chat.entity
 * fileName       : Chat
 * author         : sungjun
 * date           : 2025-01-23
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-23        kyd54       최초 생성
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Chat extends BaseEntity {
    private int roomMemberState;    // 방장(0),참여자(1),참여대기자(2),차단(9)
    private LocalDateTime roomEntranceTime; // 메시지 확인 여부 체크하기 위함
    private LocalDateTime roomExitTime; // 메시지 확인 여부 체크하기 위함

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages = new ArrayList<>(); // 빈 리스트로 초기화

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "chat_member", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "chat_id"), // `Chat` 쪽 외래키
            inverseJoinColumns = @JoinColumn(name = "member_id") // `Member` 쪽 외래키
    )
    private List<Member> memberList = new ArrayList<>();    // 빈 리스트로 초기화
}
