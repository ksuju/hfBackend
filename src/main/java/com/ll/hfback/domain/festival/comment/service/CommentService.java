package com.ll.hfback.domain.festival.comment.service;

import com.ll.hfback.domain.festival.comment.entity.Comment;
import com.ll.hfback.domain.festival.comment.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    final CommentRepository commentRepository;

    public List<Comment> searchByMemberId(Long id) {
        return commentRepository.findByMemberId(id);
    }

    // 해당 게시글에 작성된 모든 댓글 조회
    public List<Comment> searchByFestivalId(String festivalId) {
        return commentRepository.findByFestivalId(festivalId);
    }

    // 해당 댓글에 작성된 모든 답글 조회
    public List<Comment> searchBySuperCommentId(Long superCommentId) {
        return commentRepository.findBySuperCommentId(superCommentId);
    }
}
