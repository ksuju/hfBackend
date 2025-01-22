package com.ll.hfback.domain.festival.comment;

import com.ll.hfback.domain.festival.comment.service.CommentService;
import com.ll.hfback.domain.festival.post.service.PostService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CommentServiceTest {
    @Autowired
    private PostService postService;
    @Autowired
    private CommentService commentService;

    @DisplayName("1번 게시글에 가져오기")
    @Test
    void t1(){

    }

    @DisplayName("1번 게시글에 댓글들을 추가")
    @Test
    @Rollback(false)
    void t2(){

    }

}
