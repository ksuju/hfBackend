package com.ll.hfback.domain.festival.comment.controller;

import com.ll.hfback.domain.festival.comment.dto.CommentDto;
import com.ll.hfback.domain.festival.comment.form.CommentForm;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/Posts")
@RequiredArgsConstructor
public class ApiV1CommentController {
    private final CommentService commentService;

    // 해당 게시글에 작성된 모든 댓글 조회
    @GetMapping("/{festivalId}/comments")
    public List<CommentDto> getComments(@PathVariable("festivalId") String festivalId) {
        return commentService.searchByFestivalId(festivalId);
    }

    // 해당 댓글에 작성된 모든 답글 조회
    @GetMapping("/{super_comment_id}/replies")
    public List<CommentDto> getReplies(@PathVariable("super_comment_id") Long superCommentId) {
        return commentService.searchBySuperCommentId(superCommentId);
    }

    // 해당 게시글에 댓글 생성
    @PostMapping("/{festivalId}/comments")
    public ResponseEntity<String> addComment(@PathVariable("festivalId") String festivalId, @RequestBody @Valid CommentForm commentForm){
        commentService.addComment(festivalId, commentForm);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 추가되었습니다.");
    }

//    // 해당 게시글의 댓글 수정
//    @PostMapping("/{festivalId}/comments/{id}")
//    public Comment updateComment(@PathVariable String festivalId, @RequestBody Comment comment){
//
//
//    }
//
//    // 해당 게시글의 댓글 삭제
//    @DeleteMapping("/{festivalId}/comments/{id}")
//    public Comment deleteComment(@PathVariable String festivalId, @PathVariable String id){
//
//
//    }
}
