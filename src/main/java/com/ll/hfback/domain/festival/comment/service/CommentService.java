package com.ll.hfback.domain.festival.comment.service;

import com.ll.hfback.domain.festival.comment.dto.CommentDto;
import com.ll.hfback.domain.festival.comment.entity.Comment;
import com.ll.hfback.domain.festival.comment.form.AddCommentForm;
import com.ll.hfback.domain.festival.comment.repository.CommentRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;

    // 해당 게시글에 작성된 모든 댓글 조회
    @Transactional(readOnly = true)
    public List<CommentDto> searchByFestivalId(String festivalId) {
        List<Comment> comments = commentRepository.findByFestivalId(festivalId);
        return comments.stream()
                .map(this::convertToDto)
                .toList();
    }

    // 해당 댓글에 작성된 모든 답글 조회
    @Transactional(readOnly = true)
    public List<CommentDto> searchBySuperCommentId(Long superCommentId) {
        List<Comment> comments = commentRepository.findBySuperCommentId(superCommentId);
        return comments.stream()
                .map(this::convertToDto)
                .toList();
    }

    // 해당 게시글에 댓글 생성
    @Transactional
    public void addComment(String festivalId, @Valid AddCommentForm addCommentForm) {
        Comment comment = Comment.builder()
                .festivalId(festivalId)
                .member(addCommentForm.getMember())
                .content(addCommentForm.getContent())
                .commentState(true)
                .superCommentId(addCommentForm.getSuperCommentId())
                .build();

        commentRepository.save(comment);
    }

    // Comment -> CommentDto 변환
    @Transactional
    public CommentDto convertToDto(Comment comment) {
        return new CommentDto(
                comment.getMember().getNickname(), // Member의 닉네임만 가져옴
                comment.getContent(),
                comment.getCreateDate(),
                comment.getSuperCommentId()
        );
    }
}
