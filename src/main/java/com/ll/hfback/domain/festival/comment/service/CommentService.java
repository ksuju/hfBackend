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

    public List<Comment> findByMemberId(Long id) {
        return commentRepository.findByMemberId(id);
    }

    public List<Comment> findByFestivalId(String id) {
        return commentRepository.findByFestivalId(id);
    }
}
