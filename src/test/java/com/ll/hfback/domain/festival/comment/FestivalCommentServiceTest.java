package com.ll.hfback.domain.festival.comment;

import com.ll.hfback.domain.festival.comment.service.FestivalCommentService;
import com.ll.hfback.domain.festival.post.service.FestivalPostService;
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
public class FestivalCommentServiceTest {
    @Autowired
    private FestivalPostService festivalPostService;
    @Autowired
    private FestivalCommentService festivalCommentService;

    @DisplayName("1번 게시글에 가져오기")
    @Test
    @Rollback(false)
    void t1(){

    }

    @DisplayName("1번 게시글에 댓글들을 추가")
    @Test
    @Rollback(false)
    void t2(){

    }

}
