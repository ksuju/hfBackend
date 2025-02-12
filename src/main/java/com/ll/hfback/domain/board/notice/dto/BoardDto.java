package com.ll.hfback.domain.board.notice.dto;


import com.ll.hfback.domain.board.comment.dto.BoardCommentDto;
import com.ll.hfback.domain.board.comment.entity.BoardComment;
import com.ll.hfback.domain.board.notice.entity.Board;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardDto {

    private Long id;

    private LocalDateTime createDate;

    private String title;

    private String content;

    private LocalDateTime modifyDate;

    private List<BoardCommentDto> boardCommentDtos = new ArrayList<>();

    public BoardDto(Board board){
        this.id             = board.getId();
        this.createDate     = board.getCreateDate();
        this.title          = board.getTitle();
        this.content        = board.getContent();
        this.modifyDate     = board.getModifyDate();

        List<BoardComment> bcl = board.getBoardComments();

        for (BoardComment bc : bcl) {
            this.boardCommentDtos.add(new BoardCommentDto(bc));
        }
    }
}

