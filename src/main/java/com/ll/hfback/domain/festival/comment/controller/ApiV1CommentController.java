package com.ll.hfback.domain.festival.comment.controller;

import com.ll.hfback.domain.festival.comment.entity.Comment;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/festivalPosts")
@RequiredArgsConstructor
public class ApiV1CommentController {
    private final CommentService commentService;

    // 해당 게시글에 작성된 모든 댓글 조회
    @GetMapping("/comments/{festivalId}")
    public List<Comment> getComments(@PathVariable("festivalId") String festivalId) {
        List<Comment> comments = commentService.searchByFestivalId(festivalId);

        return comments;
    }

    // 해당 댓글에 작성된 모든 답글 조회
    @GetMapping("/replies/{super_comment_id}")
    public List<Comment> getReplies(@PathVariable("super_comment_id") Long superCommentId) {
        List<Comment> comments = commentService.searchBySuperCommentId(superCommentId);

        return comments;
    }

//    // 댓글 생성
//    @PostMapping("/{festivalId}/comments")
//    public Comment addComment(@PathVariable String festivalId, @RequestBody Comment comment){
//
//
//    }
//
//    // 댓글 수정
//    @PatchMapping("/{festivalId}/comments/{id}")
//    public Comment updateComment(@PathVariable String festivalId, @RequestBody Comment comment){
//
//
//    }
//
//    // 댓글 삭제
//    @DeleteMapping("/{festivalId}/comments/{id}")
//    public Comment deleteComment(@PathVariable String festivalId, @PathVariable String id){
//
//
//    }
}
