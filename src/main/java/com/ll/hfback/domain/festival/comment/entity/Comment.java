package com.ll.hfback.domain.festival.comment.entity;

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
    private String festivalId;
    private Long memberId;
    private String content;
    private Boolean commentState;
    private Long superCommentId;
}
