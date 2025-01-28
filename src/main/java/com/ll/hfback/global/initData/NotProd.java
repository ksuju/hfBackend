package com.ll.hfback.global.initData;

import com.ll.hfback.domain.festival.comment.form.CommentForm;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner applicationRunner(
            AuthService authService,
            CommentService commentService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member1 = authService.signup(
                    "test1@test.com", "1234", "강남",
                    Member.LoginType.SELF, null, null
                );
                Member member2 = authService.signup(
                    "test2@test.com", "1234", "홍길동",
                    Member.LoginType.SELF, null, null
                );
                Member member3 = authService.signup(
                    "test3@test.com", "1234", "제펫토",
                    Member.LoginType.SELF, null, null
                );

                // member1이 PF255966 공연에 작성한 테스트 댓글
                CommentForm commentForm1 = new CommentForm();
                commentForm1.setContent("이것은 member1이 작성한 테스트 댓글입니다.");
                commentForm1.setMember(member1);
                commentForm1.setSuperCommentId(null);
                commentService.addComment("PF255966",commentForm1);

                // member2가 PF255966 공연에 작성한 테스트 댓글
                CommentForm commentForm2 = new CommentForm();
                commentForm2.setContent("이것은 member2가 작성한 테스트 댓글입니다.");
                commentForm2.setMember(member2);
                commentForm2.setSuperCommentId(null);
                commentService.addComment("2938676",commentForm2);

                // member3가 PF255966 공연에서 memeber1의 댓글에 작성한 테스트 답글
                CommentForm commentForm3 = new CommentForm();
                commentForm3.setContent("이것은 member3가 작성한 테스트 답글입니다.");
                commentForm3.setMember(member3);
                commentForm3.setSuperCommentId(1L);
                commentService.addComment("2938676",commentForm3);

                // member3가 PF255966 공연에 memeber2의 댓글에 작성한 테스트 댓글
                CommentForm commentForm4 = new CommentForm();
                commentForm4.setContent("이것은 member3가 작성한 테스트 답글입니다.");
                commentForm4.setMember(member3);
                commentForm4.setSuperCommentId(2L);
                commentService.addComment("2938676",commentForm4);
            }
        };
    }
}
