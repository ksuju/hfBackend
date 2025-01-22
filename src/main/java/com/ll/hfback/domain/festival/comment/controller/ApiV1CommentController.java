package com.ll.hfback.domain.festival.comment.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/festivalPosts/{festivalId}")
@RequiredArgsConstructor
public class ApiV1CommentController {
    // 해당 게시글에 작성된 모든 댓글 조회

    // 해당 댓글에 작성된 모든 답글 조회
}
