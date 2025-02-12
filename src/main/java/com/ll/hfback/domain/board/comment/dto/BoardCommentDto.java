package com.ll.hfback.domain.board.comment.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ll.hfback.domain.board.comment.entity.BoardComment;
import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class BoardCommentDto {

    private Long id;
    private LocalDateTime createDate;
    private String content;
    private LocalDateTime modifyDate;
    private Long    authorId;
    private String  authorNickname;

    public BoardCommentDto (BoardComment boardComment){
        this.id                 = boardComment.getId();
        this.createDate         = boardComment.getCreateDate();
        this.content            = boardComment.getContent();
        this.modifyDate         = boardComment.getModifyDate();
        this.authorId           = boardComment.getAuthor().getId();
        this.authorNickname     = boardComment.getAuthor().getNickname();
    }
}
