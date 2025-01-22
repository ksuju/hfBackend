package com.ll.hfback.domain.festival.comment.repository;

import com.ll.hfback.domain.festival.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFestivalId(String festivalId);
    List<Comment> findByMemberId(Long id);
}
