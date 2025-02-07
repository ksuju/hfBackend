package com.ll.hfback.domain.board.notice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.board.comment.entity.BoardComment;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Board extends BaseEntity {

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime modifyDate;

    @OneToMany(mappedBy = "board",cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    private List<BoardComment> boardComments;

    @JsonIgnore
    @ManyToOne
    private Member author;

}