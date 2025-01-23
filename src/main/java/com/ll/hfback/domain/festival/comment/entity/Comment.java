package com.ll.hfback.domain.festival.comment.entity;

import com.ll.hfback.domain.festival.post.entity.Post;
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
public class Comment extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private Post festivalpost;
    private String festivalId;
    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "id")
    private Member member;
    private String content;
    private Boolean comment_state;
    private Long super_comment_id;
}
