package com.ll.hfback.domain.group.chat.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDateTime;

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
@Builder
public class ChatMessage {
    @Id
    private Long chatMessageId;
    // FK
    private Long groupId;
    private Long memberId;
    //

    private String chatMessageContent;
    private LocalDateTime chatMessageCreateTime;
    private int chatMessageStatus;
}
