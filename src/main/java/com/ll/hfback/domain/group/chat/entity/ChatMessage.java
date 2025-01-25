package com.ll.hfback.domain.group.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.ll.hfback.domain.group.chat.entity
 * fileName       : Chat
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
@SuperBuilder
@ToString(callSuper = true)
public class ChatMessage extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @JsonIgnore
    private ChatRoom chatRoom;

    @Column(nullable = false)
    private String chatMessageContent;

    @Column(nullable = false)
    private String nickname;

    @Builder.Default
    private int chatMessageStatus = 1; // 기본값 = 1 (안읽음 상태), 0 (읽음 상태)
}
