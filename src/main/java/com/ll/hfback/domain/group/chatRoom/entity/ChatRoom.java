package com.ll.hfback.domain.group.chatRoom.entity;

import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class ChatRoom extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;
    private String festivalId;
    private String roomTitle;
    private String roomContent;
    private int roomMemberLimit;
    private int roomState;
}
