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
                // Apis, Kopis 스케쥴러 실행
                //
                //
                //
                fetchApisScheduler.getApisApiData();
                fetchKopisScheduler.getKopisApiData();
                


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
                //1번계정 어드민 계정으로 변경
                apiV1ReportController.changeRole(member1.getId(), new ApiV1ReportController.RoleChangeRequest("ROLE_ADMIN"));

                //공지사항
                Board board = boardService.create("숨은사람친구와 함께하는 즐거운 축제 모임!","안녕하세요! 숨은사람친구에 오신 것을 환영합니다.\n" +
                        "저희 서비스는 축제를 함께 즐길 친구를 찾는 여러분을 위해 탄생했습니다.\n" +
                        "\n" +
                        "[주요 기능 안내]\n" +
                        "- 실시간 축제 정보 확인\n" +
                        "- 함께하고 싶은 축제 모임 개설/참여\n" +
                        "- 실시간 채팅으로 모임 멤버와 소통\n" +
                        "- 지도에서 가까운 축제 찾기\n" +
                        "\n" +
                        "즐거운 축제 친구 찾기, 숨은사람친구와 함께 시작해보세요!", member1);
                Board board1 = boardService.create("[필독] 즐겁고 안전한 모임을 위한 이용 수칙","모두가 즐겁고 안전한 축제 모임을 위해 아래 수칙을 꼭 지켜주세요.\n" +
                        "\n" +
                        "1. 모임 참여 시 기본 예절\n" +
                        "- 약속한 시간 준수하기\n" +
                        "- 모임 불참 시 미리 알려주기\n" +
                        "- 다른 참여자 존중하기\n" +
                        "\n" +
                        "2. 안전한 모임을 위한 주의사항\n" +
                        "- 첫 모임은 공공장소에서 진행하기\n" +
                        "- 개인정보 공유 주의하기\n" +
                        "- 금전 거래 시 주의하기\n" +
                        "\n" +
                        "3. 신고 대상이 되는 행위\n" +
                        "- 불건전한 목적의 모임 개설\n" +
                        "- 광고성 게시글 작성\n" +
                        "- 타인 비방 및 욕설\n" +
                        "\n" +
                        "즐거운 축제 모임 문화를 만들어가는 데 동참해주세요!", member1);
                Board board2 = boardService.create("[중요] 안전한 모임을 위한 가이드라인","모두의 안전하고 즐거운 모임을 위한 필수 수칙입니다.\n" +
                        "\n" +
                        "[기본 수칙]\n" +
                        "1. 첫 모임 시\n" +
                        "- 공공장소에서 만나기\n" +
                        "- 동행인과 만남 장소/시간 공유하기\n" +
                        "- 귀가 시 안전한 교통수단 이용하기\n" +
                        "\n" +
                        "2. 모임 진행 시\n" +
                        "- 음주 강요 금지\n" +
                        "- 과도한 신체 접촉 금지\n" +
                        "- 사진 촬영 시 동의 구하기\n" +
                        "\n" +
                        "3. 신고 대상\n" +
                        "- 스토킹/괴롭힘 행위\n" +
                        "- 불건전한 목적의 접근\n" +
                        "- 타인의 개인정보 무단 공유\n" +
                        "\n" +
                        "[비상연락망]\n" +
                        "- 고객센터: 1234-5678\n" +
                        "- 경찰서: 112\n" +
                        "- 긴급신고: 119\n" +
                        "\n" +
                        "즐겁고 안전한 모임 문화를 만들어가요! \uD83D\uDCAA", member1);
                Board board3 = boardService.create("[이벤트] 우리 같이 축제 가요! 인증 이벤트 \uD83C\uDF8A","즐거운 축제 모임 인증하고 선물 받아가세요!\n" +
                        "\n" +
                        "[참여 방법]\n" +
                        "1. 모임 인증하기\n" +
                        "- 축제 현장에서 단체 사진 찍기\n" +
                        "- #숨은사람친구 #축제모임 해시태그 필수\n" +
                        "- 참여 소감과 함께 게시물 작성\n" +
                        "\n" +
                        "2. 이벤트 기간\n" +
                        "- 2024년 3월 1일 ~ 3월 31일\n" +
                        "\n" +
                        "3. 당첨 선물\n" +
                        "- 1등(1명): 문화상품권 10만원\n" +
                        "- 2등(3명): 문화상품권 5만원\n" +
                        "- 3등(5명): 스타벅스 아메리카노\n" +
                        "\n" +
                        "당첨자 발표: 4월 1일\n" +
                        "많은 참여 부탁드립니다! \uD83C\uDF81", member1);
                Board board4 = boardService.create("[특집] 2024년 봄 축제 미리보기 \uD83C\uDF38","따스한 봄날, 함께 즐기면 좋을 축제를 소개합니다!\n" +
                        "\n" +
                        "[추천 봄 축제]\n" +
                        "1. 벚꽃 축제\n" +
                        "- 여의도 봄꽃축제 (4월 초)\n" +
                        "- 진해 군항제 (4월 초)\n" +
                        "- 경주 벚꽃축제 (4월 초)\n" +
                        "\n" +
                        "2. 봄꽃 축제\n" +
                        "- 고양 국제꽃박람회 (4월 말)\n" +
                        "- 태안 튤립축제 (4월 중순)\n" +
                        "- 에버랜드 튤립축제 (3월 말~)\n" +
                        "\n" +
                        "3. 봄나들이 추천 코스\n" +
                        "- 축제 관람 + 인근 카페\n" +
                        "- 벚꽃 야경 산책\n" +
                        "- 봄꽃 인생샷 명소\n" +
                        "\n" +
                        "함께할 친구를 찾아 봄축제를 즐겨보세요! \uD83C\uDF38", member1);

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
                commentService.updateComment("1", updateCommentForm, member1);
                


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
                for (int i = 0; i < 250; i++) {
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
                chatroomservice.updateChatRoom("1", updateChatRoomForm, member1);



                // 테스트 모임채팅방 참여신청
                //
                //
                // member1이 chat-room-id=2,3에 참여신청
                chatroomservice.applyChatRoom("2", member1);
                chatroomservice.applyChatRoom("3", member1);

                // member2가 chat-room-id=1,3에 참여신청
                chatroomservice.applyChatRoom("1", member2);
                chatroomservice.applyChatRoom("3", member2);

                // member3이 chat-room-id=1,2에 참여신청
                chatroomservice.applyChatRoom("1", member3);
                chatroomservice.applyChatRoom("2", member3);



                // 테스트 모임채팅방 참여신청 승인
                //
                //
                // member1이 chat-room-id=1의 모든 참여신청 승인
                chatroomservice.approveApplyChatRoom("1", "2", member1);
                chatroomservice.approveApplyChatRoom("1", "3", member1);

                // member2가 chat-room-id=2의 모든 참여신청 승인
                chatroomservice.approveApplyChatRoom("2", "1", member2);
                chatroomservice.approveApplyChatRoom("2", "3", member2);



                // 테스트 모임채팅방 참여신청 취소
                //
                //
                // member1이 chat-room-id=3에 참여신청 취소
                chatroomservice.cancelApplyChatRoom("3", member1);



                // 테스트 모임채팅방 참여신청 거절
                //
                //
                // member3이 chat-room-id=3에 member2의 참여신청 거절
                chatroomservice.refuseApplyChatRoom("3", "2", member3);



                // 테스트 모임채팅방 나가기
                //
                //
                // member1가 chat-room-id=2에서 나가기
                chatroomservice.leaveChatRoom("2", member1);



                // 테스트 모임채팅방 강퇴
                //
                //
                // member2이 chat-room-id=2에서 member3 강퇴
                chatroomservice.unqualifyChatRoom("2", "3", member2);



                // 테스트 모임채팅방 나가기(방장이 나가면 해당 모임채팅방 삭제)
                //
                //
                // member3이 chat-room-id=4에서 나가기
                chatroomservice.leaveChatRoom("4", member3);



                // 테스트 모임채팅방 방장권한 위임
                //
                //
                // member1이 chat-room-id=1에서 member3에게 방장권한 위임
                chatroomservice.delegateChatRoom("1", "3", member1);



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

            }
        };
    }
}
