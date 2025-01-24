package com.ll.hfback.domain.festival.comment.form;

import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentForm {
    @NotEmpty
    @Size(max = 500)
    private String content;

    private Member member;
    private Long superCommentId = null;
}

