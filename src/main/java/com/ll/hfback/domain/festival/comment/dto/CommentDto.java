package com.ll.hfback.domain.festival.comment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class CommentDto {
    private Long memberId;
    private String memberNickname;
    private Long commentId;
    private String content;
    private LocalDateTime createTime;
    private Long superCommentId;
}
