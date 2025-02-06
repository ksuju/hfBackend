package com.ll.hfback.domain.group.chat.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

/**
 * packageName    : com.ll.hfback.domain.group.chat.entity
 * fileName       : MessageReadStatus
 * author         : sungjun
 * date           : 2025-01-27
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-27        kyd54       최초 생성
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ChatRoomUser extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "chat_room_id", nullable = false)
    @JsonIgnore
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    @JsonIgnore
    private Member member;

    @Builder.Default
    private Long lastReadMessageId = -1L; // 마지막으로 읽은 메시지

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private ChatRoomUserStatus userLoginStatus = ChatRoomUserStatus.LOGOUT; // 채팅방 입장, 퇴장 구분
}
