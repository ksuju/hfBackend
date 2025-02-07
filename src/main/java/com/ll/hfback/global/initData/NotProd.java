package com.ll.hfback.global.initData;

import com.ll.hfback.domain.board.notice.entity.Board;
import com.ll.hfback.domain.board.notice.service.BoardService;
import com.ll.hfback.domain.festival.api.scheduler.FetchApisScheduler;
import com.ll.hfback.domain.festival.api.scheduler.FetchKopisScheduler;
import com.ll.hfback.domain.festival.comment.form.AddCommentForm;
import com.ll.hfback.domain.festival.comment.form.UpdateCommentForm;
import com.ll.hfback.domain.festival.comment.service.CommentService;
import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.group.chatRoom.form.CreateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.form.UpdateChatRoomForm;
import com.ll.hfback.domain.group.chatRoom.service.ChatRoomService;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.report.controller.ApiV1ReportController;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner applicationRunner(
            FetchApisScheduler fetchApisScheduler,
            FetchKopisScheduler fetchKopisScheduler,
            AuthService authService,
            CommentService commentService,
            ChatRoomService chatroomservice,
            BoardService boardService,
            ApiV1ReportController apiV1ReportController,
            ChatMessageService chatMessageService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {

                // Member1,2,3 생성
                Member member1 = authService.signup(
                    "test1@test.com", "1234", "강남",
                    Member.LoginType.SELF, null, null, null
                );
                Member member2 = authService.signup(
                    "test2@test.com", "1234", "홍길동",
                    Member.LoginType.SELF, null, null, null
                );
                Member member3 = authService.signup(
                    "test3@test.com", "1234", "제펫토",
                    Member.LoginType.SELF, null, null, null
                );

                apiV1ReportController.changeRole(member1.getId(), new ApiV1ReportController.RoleChangeRequest("ROLE_ADMIN"));

                Board board = boardService.create("이용약관 변경 안내","안녕하세요, 고객님.\n" +
                        "저희 서비스의 이용약관이 다음과 같이 변경되었습니다.\n" +
                        "변경 사항:\n" +
                        "\n" +
                        "서비스 이용 시 개인정보 처리 방식에 대한 구체적인 안내 추가\n" +
                        "회원 탈퇴 절차에 대한 세부 규정 변경\n" +
                        "적용일: 2025년 2월 10일부터\n" +
                        "변경된 이용약관은 홈페이지에서 확인하실 수 있습니다.\n" +
                        "서비스 이용에 참고해 주시기 바랍니다. 감사합니다." +
                        "이용에 불편을 드려 죄송합니다. 감사합니다.", member1);
                Board board1 = boardService.create("서버 점검 안내","안녕하세요, 고객님.\n" +
                        "저희 서비스의 안정성을 위한 서버 점검이 예정되어 있습니다.\n" +
                        "점검 시간: 2025년 2월 7일 (금) 03:00 ~ 05:00 (KST)\n" +
                        "점검 동안 서비스 이용이 일시적으로 중단됩니다.\n" +
                        "이용에 불편을 드려 죄송하며, 빠른 시간 내에 점검을 완료하도록 하겠습니다. 감사합니다.", member1);
                Board board2 = boardService.create("이용약관 개정 안내","안녕하세요, 고객님.\n" +
                        "저희 서비스의 이용약관이 아래와 같이 개정되었습니다.\n" +
                        "주요 변경 사항:\n" +
                        "\n" +
                        "서비스 이용 시 책임 및 의무에 대한 명확한 정의 추가\n" +
                        "서비스 제공 범위에 대한 세부 규정 변경\n" +
                        "적용일: 2025년 2월 15일부터\n" +
                        "개정된 약관은 서비스 내 \"약관 및 정책\"에서 확인 가능합니다.\n" +
                        "이용에 참고 부탁드리며, 변경 사항에 대해 궁금한 점이 있으시면 고객센터로 문의해 주세요. 감사합니다.", member1);
                Board board3 = boardService.create("긴급 점검 안내","안녕하세요, 고객님.\n" +
                        "예기치 못한 시스템 오류로 인한 긴급 점검이 진행됩니다.\n" +
                        "점검 시간: 2025년 2월 6일 (목) 22:00 ~ 23:30 (KST)\n" +
                        "점검 기간 동안 서비스 이용에 불편이 있을 수 있습니다.\n" +
                        "빠른 시간 내에 문제를 해결할 수 있도록 최선을 다하겠습니다. 양해 부탁드립니다.", member1);
                Board board4 = boardService.create("정기 서버 점검 안내","안녕하세요, 고객님.\n" +
                        "저희는 서비스 품질 향상을 위해 정기 서버 점검을 진행합니다.\n" +
                        "점검 시간: 2025년 2월 8일 (토) 01:00 ~ 03:00 (KST)\n" +
                        "점검 시간 동안 일부 기능이 일시적으로 중단될 수 있습니다.\n" +
                        "서비스 이용에 불편을 드려 죄송하며, 점검이 완료되는 대로 정상 서비스를 제공하겠습니다. 감사합니다.ㅌ", member1);



                // 테스트 댓글&답글 생성
                //
                //
                // member1이 PF256158 공연게시글에 작성한 테스트 댓글
                AddCommentForm addCommentForm1 = new AddCommentForm();
                addCommentForm1.setContent("이것은 member1이 작성한 테스트 댓글입니다.");
                addCommentForm1.setSuperCommentId(null);
                commentService.addComment("PF256158", addCommentForm1, member1);

                // member2가 PF256158 공연게시글에 작성한 테스트 댓글
                AddCommentForm addCommentForm2 = new AddCommentForm();
                addCommentForm2.setContent("이것은 member2가 작성한 테스트 댓글입니다.");
                addCommentForm2.setSuperCommentId(null);
                commentService.addComment("PF256158", addCommentForm2, member2);

                // member3가 PF256158 공연게시글에서 memeber1의 댓글에 작성한 테스트 답글
                AddCommentForm addCommentForm3 = new AddCommentForm();
                addCommentForm3.setContent("이것은 member3이 작성한 테스트 답글입니다.");
                addCommentForm3.setSuperCommentId(1L);
                commentService.addComment("PF256158", addCommentForm3, member3);

                // member3가 PF256158 공연게시글에서 memeber2의 댓글에 작성한 테스트 답글
                AddCommentForm addCommentForm4 = new AddCommentForm();
                addCommentForm4.setContent("이것은 member3이 작성한 테스트 답글입니다.");
                addCommentForm4.setSuperCommentId(2L);
                commentService.addComment("PF256158", addCommentForm4, member3);



                // 테스트 댓글 수정
                //
                //
                // member1이 수정한 테스트 댓글(comment-id=1)
                UpdateCommentForm updateCommentForm = new UpdateCommentForm();
                updateCommentForm.setContent("이것은 member1이 다시 수정한 테스트 댓글입니다.");
                commentService.updateComment(1L, updateCommentForm, member1);
                


                // 테스트 모임채팅방 생성
                //
                //
                // member1이 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm1 = new CreateChatRoomForm();
                createChatRoomForm1.setRoomTitle("이것은 member1이 작성한 테스트 제목입니다.");
                createChatRoomForm1.setRoomContent("이것은 member1이 작성한 테스트 본문입니다.");
                createChatRoomForm1.setRoomMemberLimit(10L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm1, member1);

                // member2가 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm2 = new CreateChatRoomForm();
                createChatRoomForm2.setRoomTitle("이것은 member2가 작성한 테스트 제목입니다.");
                createChatRoomForm2.setRoomContent("이것은 member2가 작성한 테스트 본문입니다.");
                createChatRoomForm2.setRoomMemberLimit(20L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm2, member2);

                // member3가 PF256158 공연게시글에서 만든 모임채팅방
                CreateChatRoomForm createChatRoomForm3 = new CreateChatRoomForm();
                createChatRoomForm3.setRoomTitle("이것은 member3이 작성한 매우매우매우매우매우매우매우매우매우매우매우매우 긴 테스트 제목입니다.");
                createChatRoomForm3.setRoomContent("이것은 member3이 작성한  매우매우매우매우매우매우매우매우매우매우매우매우 긴 테스트 본문입니다.");
                createChatRoomForm3.setRoomMemberLimit(30L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm3, member3);

                // member3가 PF256158 공연게시글에서 만든 모임채팅방4
                CreateChatRoomForm createChatRoomForm4 = new CreateChatRoomForm();
                createChatRoomForm4.setRoomTitle("이것은 member3이 작성한 테스트 제목22입니다.");
                createChatRoomForm4.setRoomContent("이것은 member3이 작성한 테스트 본문22입니다.");
                createChatRoomForm4.setRoomMemberLimit(40L);
                chatroomservice.createChatRoom("PF256158", createChatRoomForm4, member3);

                // member3가 PF256158 공연게시글에서 만든 모임채팅방(1-25)
                for (int i = 0; i < 25; i++) {
                    CreateChatRoomForm createChatRoomForm = new CreateChatRoomForm();

                    createChatRoomForm.setRoomTitle(String.format("이것은 member3이 작성한 테스트 제목%d 입니다.", i+1));
                    createChatRoomForm.setRoomContent(String.format("이것은 member3이 작성한 테스트 본문%d 입니다.", i+1));
                    createChatRoomForm.setRoomMemberLimit(70L);
                    chatroomservice.createChatRoom("PF256158", createChatRoomForm, member3);
                }



                // 테스트 모임채팅방 수정
                //
                //
                // member1이 수정한 테스트 모임채팅방(chat-room-id=1)
                UpdateChatRoomForm updateChatRoomForm = new UpdateChatRoomForm();
                updateChatRoomForm.setRoomTitle("이것은 member1이 다시 수정한 테스트 제목입니다.");
                updateChatRoomForm.setRoomContent("이것은 member1이 다시 수정한 테스트 본문입니다.");
                updateChatRoomForm.setRoomMemberLimit(100L);
                chatroomservice.updateChatRoom(1L, updateChatRoomForm, member1);



                // 테스트 모임채팅방 참여신청
                //
                //
                // member1이 chat-room-id=2,3에 참여신청
                chatroomservice.applyChatRoom(2L, member1);
                chatroomservice.applyChatRoom(3L, member1);

                // member2가 chat-room-id=1,3에 참여신청
                chatroomservice.applyChatRoom(1L, member2);
                chatroomservice.applyChatRoom(3L, member2);

                // member3이 chat-room-id=1,2에 참여신청
                chatroomservice.applyChatRoom(1L, member3);
                chatroomservice.applyChatRoom(2L, member3);



                // 테스트 모임채팅방 참여신청 승인
                //
                //
                // member1이 chat-room-id=1의 모든 참여신청 승인
                chatroomservice.approveApplyChatRoom(1L, "2", member1);
                chatroomservice.approveApplyChatRoom(1L, "3", member1);

                // member2가 chat-room-id=2의 모든 참여신청 승인
                chatroomservice.approveApplyChatRoom(2L, "1", member2);
                chatroomservice.approveApplyChatRoom(2L, "3", member2);



                // 테스트 모임채팅방 참여신청 취소
                //
                //
                // member1이 chat-room-id=3에 참여신청 취소
                chatroomservice.cancelApplyChatRoom(3L, member1);



                // 테스트 모임채팅방 참여신청 거절
                //
                //
                // member3이 chat-room-id=3에 member2의 참여신청 거절
                chatroomservice.refuseApplyChatRoom(3L, "2", member3);



                // 테스트 모임채팅방 나가기
                //
                //
                // member1가 chat-room-id=2에서 나가기
                chatroomservice.leaveChatRoom(2L, member1);



                // 테스트 모임채팅방 강퇴
                //
                //
                // member2이 chat-room-id=2에서 member3 강퇴
                chatroomservice.unqualifyChatRoom(2L, "3", member2);



                // 테스트 모임채팅방 나가기(방장이 나가면 해당 모임채팅방 삭제)
                //
                //
                // member3이 chat-room-id=4에서 나가기
                chatroomservice.leaveChatRoom(4L, member3);



                // 테스트 모임채팅방 방장권한 위임
                //
                //
                // member1이 chat-room-id=1에서 member3에게 방장권한 위임
                chatroomservice.delegateChatRoom(1L, "3", member1);



                // 위 모임채팅방 테스트 결과 예측
                //
                //
                //  chat-room-id=1의 참여자는 member1,2,3이고 방장은 member3으로 변경
                //  chat-room-id=2는 방장(member2)만 남음 (member1은 나감, member3은 강퇴당함)
                //  chat-room-id=3은 방장(member3)만 남음 (member1은 참여신청 취소, member2는 참여신청 거절당함)
                //  chat-room-id=4는 삭제됨



                // 1번 채팅방 채팅 테스트 (유저 셋이서 하는 대화)
                //
                //
                // 3명의 유저가 대화하는 채팅 메시지 추가 (30개)
                List<String> messages = List.of(
                        "안녕하세요! 오늘 공연 잘 보셨나요?",
                        "네, 정말 멋졌어요!",
                        "저도 너무 감동받았어요. 특히 마지막 장면에서요.",
                        "맞아요, 그 장면에서 눈물이 날 뻔했어요.",
                        "혹시 다음 공연도 같이 보실래요?",
                        "좋아요! 다음 공연은 언제인가요?",
                        "다음 주 토요일로 알고 있어요.",
                        "그럼 그때 같이 보기로 해요!",
                        "공연 후에 다 같이 저녁도 먹으면 좋겠네요.",
                        "좋은 생각이에요! 어디서 먹을까요?",
                        "강남역 근처에 괜찮은 식당이 많아요.",
                        "거기 좋네요. 강남역에서 만나기로 해요.",
                        "몇 시에 만날까요?",
                        "공연이 7시에 끝나니까 7시 반에 만나는 건 어때요?",
                        "좋아요. 그럼 7시 반에 강남역에서 봬요!",
                        "혹시 공연 티켓은 다들 예약하셨나요?",
                        "아직이요. 지금 예약하려고요.",
                        "저도 지금 바로 예약할게요.",
                        "저는 이미 예약했어요. 2구역에 자리 잡았어요.",
                        "그럼 저희도 2구역으로 예약해야겠네요.",
                        "같은 구역이면 더 재밌을 거예요!",
                        "저도 그렇게 생각해요. 공연이 기대돼요!",
                        "혹시 다음 주 공연이 몇 시에 시작하나요?",
                        "오후 5시 시작이에요. 공연장에 4시쯤 도착하면 될 것 같아요.",
                        "그럼 4시쯤 공연장에서 만나기로 해요.",
                        "좋아요. 공연 전에 간단히 커피 마시면서 얘기해요.",
                        "저 커피 좋아해요! 기대돼요.",
                        "그럼 공연장에서 뵐게요! 다들 좋은 하루 되세요.",
                        "네, 좋은 하루 되세요! 다음 주에 만나요.",
                        "감사합니다. 모두 다음 주에 뵈어요!"
                );
                //
                //
                for (int i = 0; i < messages.size(); i++) {
                    Member sender = switch (i % 3) {  // switch 표현식 사용
                        case 0 -> member1;
                        case 1 -> member2;
                        default -> member3;
                    };

                    RequestMessage requestMessage = new RequestMessage(messages.get(i));

                    // 메시지 저장 호출
                    chatMessageService.writeMessage(1L, requestMessage, sender
                            );
                }



                // Apis, Kopis 스케쥴러 실행
                //
                //
                //
                fetchApisScheduler.getApisApiData();
                fetchKopisScheduler.getKopisApiData();
            }
        };
    }
}
