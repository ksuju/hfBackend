package com.ll.hfback.global.initData;

import com.ll.hfback.domain.festival.comment.form.AddCommentForm;
import com.ll.hfback.domain.festival.comment.form.UpdateCommentForm;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.Gender;
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
            CommentService commentService,
            ChatRoomService chatroomservice
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member1 = authService.signup(
                    new SignupRequest(
                        "test1@test.com", "1234", "강남",
                        Gender.M, true, "010-1111-1111"
                    )
                );
                Member member2 = authService.signup(
                    new SignupRequest(
                        "test2@test.com", "1234", "남영동",
                        Gender.M, false, "010-2222-2222"
                    )
                );
                Member member3 = authService.signup(
                    new SignupRequest(
                        "test3@test.com", "1234", "역삼",
                        Gender.W, true, "010-3333-3333"
                    )
                );



                // 테스트 댓글&답글 생성
                //
                //
                // member1이 PF256158 공연게시글에 작성한 테스트 댓글
                AddCommentForm addCommentForm1 = new AddCommentForm();
                addCommentForm1.setContent("이것은 member1이 작성한 테스트 댓글입니다.");
                addCommentForm1.setMember(member1);
                addCommentForm1.setSuperCommentId(null);
                commentService.addComment("PF256158", addCommentForm1);

                // member2가 PF256158 공연게시글에 작성한 테스트 댓글
                AddCommentForm addCommentForm2 = new AddCommentForm();
                addCommentForm2.setContent("이것은 member2가 작성한 테스트 댓글입니다.");
                addCommentForm2.setMember(member2);
                addCommentForm2.setSuperCommentId(null);
                commentService.addComment("PF256158", addCommentForm2);

                // member3가 PF256158 공연게시글에서 memeber1의 댓글에 작성한 테스트 답글
                AddCommentForm addCommentForm3 = new AddCommentForm();
                addCommentForm3.setContent("이것은 member3이 작성한 테스트 답글입니다.");
                addCommentForm3.setMember(member3);
                addCommentForm3.setSuperCommentId(1L);
                commentService.addComment("PF256158", addCommentForm3);

                // member3가 PF256158 공연게시글에서 memeber2의 댓글에 작성한 테스트 답글
                AddCommentForm addCommentForm4 = new AddCommentForm();
                addCommentForm4.setContent("이것은 member3이 작성한 테스트 답글입니다.");
                addCommentForm4.setMember(member3);
                addCommentForm4.setSuperCommentId(2L);
                commentService.addComment("PF256158", addCommentForm4);



                // 테스트 댓글 수정
                //
                //
                // member1이 수정한 테스트 댓글(comment-id=1)
                UpdateCommentForm updateCommentForm = new UpdateCommentForm();
                updateCommentForm.setContent("이것은 member1이 다시 수정한 테스트 댓글입니다.");
                commentService.updateComment("1", updateCommentForm);
                

                
                // 테스트 모임채팅방 생성
                //
                //
                // member1이 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm1 = new CreateChatRoomForm();
                createChatRoomForm1.setMember(member1);
                createChatRoomForm1.setRoomTitle("이것은 member1이 작성한 테스트 제목입니다.");
                createChatRoomForm1.setRoomContent("이것은 member1이 작성한 테스트 본문입니다.");
                createChatRoomForm1.setRoomMemberLimit(10L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm1);

                // member2가 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm2 = new CreateChatRoomForm();
                createChatRoomForm2.setMember(member2);
                createChatRoomForm2.setRoomTitle("이것은 member2가 작성한 테스트 제목입니다.");
                createChatRoomForm2.setRoomContent("이것은 member2가 작성한 테스트 본문입니다.");
                createChatRoomForm2.setRoomMemberLimit(20L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm2);

                // member3가 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm3 = new CreateChatRoomForm();
                createChatRoomForm3.setMember(member3);
                createChatRoomForm3.setRoomTitle("이것은 member3이 작성한 테스트 제목입니다.");
                createChatRoomForm3.setRoomContent("이것은 member3이 작성한 테스트 본문입니다.");
                createChatRoomForm3.setRoomMemberLimit(30L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm3);
            }
        };
    }
}
