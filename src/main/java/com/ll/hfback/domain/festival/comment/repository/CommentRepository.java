package com.ll.hfback.domain.festival.comment.repository;

import com.ll.hfback.domain.festival.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByMemberId(Long id);

    // 해당 게시글에 작성된 모든 댓글 조회
    List<Comment> findByFestivalId(String festivalId);

    // 해당 댓글에 작성된 모든 답글 조회
    List<Comment> findBySuperCommentId(Long superCommentId);


}
