package com.ll.hfback.domain.board.comment.service;

import com.ll.hfback.domain.board.comment.entity.BoardComment;
import com.ll.hfback.domain.board.comment.repository.BoardCommentRepository;
import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.board.notice.service.BoardService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class BoardCommentService {

    private final BoardService boardService;
    private final BoardCommentRepository boardCommentRepository;


    //댓글 작성
    public BoardComment create(Long id, String content, Member member) {
        Board board = this.boardService.view(id);
        if (board != null) {
            BoardComment boardComment = new BoardComment();
            boardComment.setContent(content);
            boardComment.setAuthor(member);
            boardComment.setBoard(board);
            return this.boardCommentRepository.save(boardComment);
        } else {
            throw new ServiceException(ErrorCode.BOARD_NOT_FOUND);
        }
    }
    //댓글 조회
    public BoardComment getComment(Long id) {
        Optional<BoardComment> boardComment = this.boardCommentRepository.findById(id);
        if (boardComment.isPresent()) {
            return boardComment.get();
        } else {
            throw new ServiceException(ErrorCode.BOARDCOMMENT_NOT_FOUND);
        }
    }
    //댓글 수정
    public BoardComment modify(BoardComment boardComment, String content, Member member) {
        Optional<BoardComment> b = this.boardCommentRepository.findById(boardComment.getId());
        if (b.isPresent()) {
            BoardComment getBoardComment = b.get();
            if (!getBoardComment.getAuthor().equals(member)) {
                throw new ServiceException(ErrorCode.INVALID_ROLE);
            }
            boardComment.setContent(content);
            boardComment.setModifyDate(LocalDateTime.now());
            boardComment.setAuthor(member);
            return this.boardCommentRepository.save(boardComment);
        } else {
            throw new ServiceException(ErrorCode.BOARDCOMMENT_NOT_FOUND);
        }
    }
    //댓글 삭제
    public void delete(BoardComment boardComment,Member member) {
        if(boardComment == null){
            throw new ServiceException(ErrorCode.BOARDCOMMENT_NOT_FOUND);
        }
        if(!boardComment.getAuthor().equals(member)) {
            throw new ServiceException(ErrorCode.INVALID_ROLE);
        }
        this.boardCommentRepository.delete(boardComment);
    }
}
