package com.ll.hfback.domain.board.comment.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
public class BoardComment extends BaseEntity {

    @JsonSerialize(using = ToStringSerializer.class)
    private String content;

    private LocalDateTime modifyDate;

    @JsonIgnore
    @ManyToOne
    private Board board;

    @JsonIgnore
    @ManyToOne
    private Member author;
}
