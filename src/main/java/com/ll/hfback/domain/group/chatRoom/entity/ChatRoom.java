package com.ll.hfback.domain.group.chatRoom.entity;

import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.group.chatRoom.converter.StringDoubleListConverter;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_festival_id", nullable = false)
    private Post post;
    private String roomTitle;
    @Column(length = 500)
    private String roomContent;
    private Long roomMemberLimit;
    private Long roomState;
    @Convert(converter = StringDoubleListConverter.class)
    private List<List<String>> joinMemberIdNickNameList;
    @Convert(converter = StringDoubleListConverter.class)
    private List<List<String>> waitingMemberIdNickNameList;
}
