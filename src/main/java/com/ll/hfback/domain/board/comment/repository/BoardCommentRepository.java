package com.ll.hfback.domain.board.comment.repository;


import com.ll.hfback.domain.board.comment.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardCommentRepository extends JpaRepository<BoardComment, Long> {
}
