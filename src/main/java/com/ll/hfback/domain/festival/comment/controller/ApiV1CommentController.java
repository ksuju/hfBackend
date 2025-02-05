package com.ll.hfback.domain.festival.comment.controller;

import com.ll.hfback.domain.festival.comment.dto.CommentDto;
import com.ll.hfback.domain.festival.comment.form.AddCommentForm;
import com.ll.hfback.domain.festival.comment.form.UpdateCommentForm;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.webMvc.LoginUser;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1CommentController {
    private final CommentService commentService;

    // 해당 게시글에 작성된 모든 댓글 조회
    @GetMapping("/{festival-id}/comments")
    public List<CommentDto> getComments(@PathVariable("festival-id") String festivalId) {
        return commentService.searchByFestivalId(festivalId);
    }

    // 해당 댓글에 작성된 모든 답글 조회
    @GetMapping("/replies/{super-comment-id}")
    public List<CommentDto> getReplies(@PathVariable("super-comment-id") Long superCommentId) {
        return commentService.searchBySuperCommentId(superCommentId);
    }

    // 해당 게시글에 댓글 생성
    @PostMapping("/{festival-id}/comments")
    public ResponseEntity<String> addComment(@PathVariable("festival-id") String festivalId, @RequestBody @Valid AddCommentForm addCommentForm, @LoginUser Member loginUser) {
        commentService.addComment(festivalId, addCommentForm, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 추가되었습니다.");
    }

    // 해당 댓글 수정
    @PostMapping("/update-comment/{comment-id}")
    public ResponseEntity<String> updateComment(@PathVariable("comment-id") Long commentId, @RequestBody @Valid UpdateCommentForm updateCommentForm, @LoginUser Member loginUser) {
        commentService.updateComment(commentId, updateCommentForm, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 수정되었습니다.");
    }

    // 해당 댓글 삭제
    @GetMapping("/delete-comment/{comment-id}")
    public ResponseEntity<String> deleteComment(@PathVariable("comment-id") Long commentId, @LoginUser Member loginUser) {
        commentService.deleteComment(commentId, loginUser);
        return ResponseEntity.status(HttpStatus.CREATED).body("댓글이 성공적으로 삭제되었습니다.");
    }
}
