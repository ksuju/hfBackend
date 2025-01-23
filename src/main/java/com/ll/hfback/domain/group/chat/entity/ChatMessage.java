package com.ll.hfback.domain.group.chat.entity;

import com.ll.hfback.domain.group.room.entity.Room;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@SuperBuilder
@ToString(callSuper = true)
public class ChatMessage extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Chat chat;   // Chat 엔티티와 연결

    private String chatMessageContent;

    @Builder.Default
    private int chatMessageStatus = 1; // 기본값 = 1 (안읽음 상태), 0 (읽음 상태)
}
