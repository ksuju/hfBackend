package com.ll.hfback.domain.group.chat.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * packageName    : com.ll.hfback.domain.group.chat.service
 * fileName       : ChatMessageServiceTest
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@SpringBootTest // 시간관계상 TDD 중단
@ActiveProfiles("test") // 테스트용 application 설정 파일 사용
@Transactional // 테스트 후 데이터 롤백
class ChatMessageServiceTest {
//
//    @Autowired
//    private ChatMessageRepository chatMessageRepository;
//
//    @Autowired
//    private RoomRepository roomRepository;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//
//    @Test
//    @DisplayName("모임방 생성 시 일대일 관계인 채팅방이 같이 생성되는지 테스트")
//    void createRoomTest() {
//        // given : 테스트 준비 과정
//
//        // when : 채팅 메시지 저장
//
//        // then : 저장된 데이터 검증
//
//    }
//
//    @Test
//    @DisplayName("채팅 메시지 작성 테스트")
//    void writeMessageTest() {
//        // given : 테스트 준비 과정
//
//        // when : 채팅 메시지 저장
//
//        // then : 저장된 데이터 검증
//
//    }
}