package com.ll.hfback.domain.group.room.entity;

import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.group.chat.entity.Chat;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

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
@SuperBuilder
@ToString(callSuper = true)
public class Room extends BaseEntity {

    @ManyToOne
    private KopisFesEntity festival;

    @ManyToOne
    private Member member;

    @Column(nullable = false)
    private String   roomTitle;

    @Column(nullable = false)
    private String   roomContent;

    @Column(nullable = false)
    private int      roomMemberLimit;

    @Column(nullable = false)
    private int      roomState;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Chat chat;
}
