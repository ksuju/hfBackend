package com.ll.hfback.domain.group.chat.serviceImpl;

import com.ll.hfback.domain.group.chat.entity.ChatRoomUser;
import com.ll.hfback.domain.group.chat.entity.QChatMessage;
import com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus;
import com.ll.hfback.domain.group.chat.repository.ChatRoomUserRepository;
import com.ll.hfback.domain.group.chat.response.ResponseMemberStatus;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.request.MessageReadStatusRequest;
import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.request.RequestMessage;
import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.service.ChatMessageService;
import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import com.ll.hfback.domain.group.chatRoom.repository.ChatRoomRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.rsData.RsData;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * packageName    : com.ll.hfback.domain.group.chat.service
 * fileName       : ChatMessageService
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageServiceImpl.class.getName());
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomUserRepository chatRoomUserRepository;

    // 채팅 메시지 작성
    @Transactional
    public RsData<Object> writeMessage(Long chatRoomId, RequestMessage requestMessage, Member loginUser) {
        try {

            // 1️⃣ 채팅방 존재 여부 확인
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElse(null);
            if (chatRoom == null) {
                return new RsData<>("404", "존재하지 않는 채팅방입니다.");
            }
            
            // 2️⃣ 채팅방 참여 여부 확인
            if (!chatRoom.getJoinMemberIdList().contains(loginUser.getId().toString())) {
                return new RsData<>("403", "해당 채팅방에 참여하지 않은 사용자입니다.");
            }

            // 3️⃣ 빈 메시지 또는 250자 초과 메시지 검사
            if (requestMessage.getContent() == null || requestMessage.getContent().trim().isEmpty()) {
                return new RsData<>("400", "채팅 메시지는 비어 있을 수 없습니다.");
            }
            if (requestMessage.getContent().length() > 250) {
                return new RsData<>("400", "채팅 메시지는 250자를 넘을 수 없습니다.");
            }

            // 4️⃣ 메시지 저장 및 전송
            ChatMessage chatMessage = ChatMessage.builder()
                    .chatRoom(chatRoom)
                    .nickname(loginUser.getNickname())
                    .chatMessageContent(requestMessage.getContent())
                    .build();
            chatMessageRepository.save(chatMessage);
            simpMessagingTemplate.convertAndSend("/topic/chat/" + chatRoomId, chatMessage);

            logger.info("채팅 메시지 작성 성공");
            return new RsData<>("200", "채팅 메시지 작성 성공");
        } catch (Exception e) {
            logger.error("채팅 메시지 작성 실패: ", e);
            return new RsData<>("500", "서버 내부 오류가 발생했습니다.");
        }
    }

    // 채팅 메시지 가져오기
    @Transactional(readOnly = true)
    public Page<ResponseMessage> readMessages(Long chatRoomId, int page, Member loginUser) {
        try {
            // 1️⃣ 채팅방 존재 여부 확인
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 채팅방입니다.")
            );

            // 2️⃣ 채팅방 참여 여부 확인
            if (!chatRoom.getJoinMemberIdList().contains(loginUser.getId().toString())) {
                throw new IllegalArgumentException("해당 채팅방에 참여하지 않은 사용자입니다.");
            }

            Page<ChatMessage> chatMessages = chatMessageRepository.findByChatRoomId(chatRoomId, customPaging(page));
            logger.info("채팅 메시지 가져오기 성공");
            // ChatMessage -> RequestMessage 변환
            return chatMessages.map(chatMessage ->
                    new ResponseMessage(chatMessage.getNickname(),
                            chatMessage.getChatMessageContent(),
                            chatMessage.getCreateDate(),
                            chatMessage.getId()
                    ));
        } catch (Exception e) {
            logger.info("채팅 메시지 가져오기 실패");
            throw e;
        }
    }

    // 고정된 페이지 크기 10으로 Pageable 객체를 생성 (메시지 불러올 때 사용할 커스텀 페이징)
    public Pageable customPaging(int page) {
        try {
            // 내림차순 정렬 (createdAt 기준)
            Sort sort = Sort.by(Sort.Order.desc("createDate"));
            logger.info("커스텀 페이징 성공");
            
            // 페이지 번호와 크기, 정렬을 포함하여 Pageable 객체 생성
            return PageRequest.of(page, 10, sort);
        } catch (Exception e) {
            logger.info("커스텀 페이징 실패");
            throw e;
        }
    }

    // 조건에 따른 채팅 메시지 검색 기능
    public Page<ResponseMessage> searchMessages(Long chatRoomId,
                                                int page,
                                                MessageSearchKeywordsRequest messageSearchKeywordsRequest, Member loginUser) {
        try {
            // 1️⃣ 채팅방 존재 여부 확인
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 채팅방입니다.")
            );

            // 2️⃣ 채팅방 참여 여부 확인
            if (!chatRoom.getJoinMemberIdList().contains(loginUser.getId().toString())) {
                throw new IllegalArgumentException("해당 채팅방에 참여하지 않은 사용자입니다.");
            }

            QChatMessage qChatMessage = QChatMessage.chatMessage; // QueryDSL 메타 모델 객체

            BooleanBuilder builder = new BooleanBuilder();

            // 검색어(keyword)가 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest != null && messageSearchKeywordsRequest.getKeyword() != null) {
                String keyword = messageSearchKeywordsRequest.getKeyword();
                builder.and(qChatMessage.chatMessageContent.containsIgnoreCase(keyword)); // 대소문자 구분 없이 검색
            }

            // 닉네임(nickname)이 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest.getNickname() != null) {
                String nickname = messageSearchKeywordsRequest.getNickname();
                builder.and(qChatMessage.nickname.containsIgnoreCase(nickname)); // 대소문자 구분 없이 검색
            }

            // 날짜 처리: 날짜를 LocalDateTime으로 변환하여 비교
            // startDate만 있을 경우
            if (messageSearchKeywordsRequest.getStartDate() != null) {
                LocalDate startDate = messageSearchKeywordsRequest.getStartDate();
                LocalDateTime startDateTime = startDate.atStartOfDay(); // startDate를 00:00:00로 변환
                builder.and(qChatMessage.createDate.goe(startDateTime)); // startDate부터 최근까지 메시지 검색
            }

            // endDate만 있을 경우
            if (messageSearchKeywordsRequest.getEndDate() != null) {
                LocalDate endDate = messageSearchKeywordsRequest.getEndDate();
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59, 999999999); // endDate를 23:59:59로 변환
                builder.and(qChatMessage.createDate.loe(endDateTime)); // 처음부터 endDate까지 메시지 검색
            }

            // startDate와 endDate 모두 있을 경우
            if (messageSearchKeywordsRequest.getStartDate() != null && messageSearchKeywordsRequest.getEndDate() != null) {
                LocalDate startDate = messageSearchKeywordsRequest.getStartDate();
                LocalDate endDate = messageSearchKeywordsRequest.getEndDate();
                LocalDateTime startDateTime = startDate.atStartOfDay(); // startDate를 00:00:00로 변환
                LocalDateTime endDateTime = endDate.atTime(23, 59, 59, 999999999); // endDate를 23:59:59로 변환
                builder.and(qChatMessage.createDate.between(startDateTime, endDateTime)); // 두 날짜 사이의 메시지 검색
            }

            // chatRoomId에 맞는 메시지 필터링
            builder.and(qChatMessage.chatRoom.id.eq(chatRoomId));

            // 페이지 처리와 함께 검색된 메시지 조회
            Page<ChatMessage> searchMessages = chatMessageRepository.findAll(builder, customPaging(page));

            logger.info("조건에 따른 채팅 메시지 검색 성공");
            // ChatMessage -> RequestMessage 변환
            return searchMessages.map(chatMessage ->
                    new ResponseMessage(chatMessage.getNickname(),
                            chatMessage.getChatMessageContent(),
                            chatMessage.getCreateDate(),
                            chatMessage.getId()));
        } catch (Exception e) {
            logger.info("조건에 따른 채팅 메시지 검색 실패");
            throw e;
        }
    }

    // 메시지 읽음/안읽음 상태 확인
    @Transactional
    public void messageReadStatus(Long chatRoomId, MessageReadStatusRequest messageReadStatusRequest, Member loginUser) {
        try {
            // 1️⃣ 채팅방 존재 여부 확인
            ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                    new IllegalArgumentException("존재하지 않는 채팅방입니다.")
            );

            // 2️⃣ 채팅방 참여 여부 확인
            if (!chatRoom.getJoinMemberIdList().contains(loginUser.getId().toString())) {
                throw new IllegalArgumentException("해당 채팅방에 참여하지 않은 사용자입니다.");
            }

            ChatRoomUser readStatus = chatRoomUserRepository
                    .findByChatRoomIdAndMemberId(chatRoomId, messageReadStatusRequest.getMemberId())
                    .orElse(ChatRoomUser.builder()
                            .chatRoom(chatRoomRepository.findById(chatRoomId).orElseThrow(() -> new RuntimeException("ChatRoom not found")))
                            .member(memberRepository.findById(messageReadStatusRequest.getMemberId()).orElseThrow(() -> new RuntimeException("Member not found")))
                            .lastReadMessageId(messageReadStatusRequest.getMessageId())
                            .build());

            readStatus.setLastReadMessageId(messageReadStatusRequest.getMessageId());
            chatRoomUserRepository.save(readStatus);

            logger.info("ChatRoom ID: {}, Member ID: {} - 마지막 읽은 메시지 ID가 성공적으로 업데이트되었습니다.",
                    chatRoomId,
                    messageReadStatusRequest.getMemberId());
        } catch (Exception e) {
            logger.error("마지막 읽은 메시지 업데이트 실패");
            throw e;
        }
    }

    // 채팅방 멤버 로그인/로그아웃 상태 확인
    public List<ResponseMemberStatus> memberLoginStatus(Long chatRoomId, Member loginUser) {
        // 1️⃣ 채팅방 존재 여부 확인
        ChatRoom chatRoom = chatRoomRepository.findById(chatRoomId).orElseThrow(() ->
                new IllegalArgumentException("존재하지 않는 채팅방입니다.")
        );

        // 2️⃣ 채팅방 참여 여부 확인
        if (!chatRoom.getJoinMemberIdList().contains(loginUser.getId().toString())) {
            throw new IllegalArgumentException("해당 채팅방에 참여하지 않은 사용자입니다.");
        }

        // 채팅방에 속한 사용자 정보 가져오기
        List<ChatRoomUser> chatRoomUsers =
                chatRoomUserRepository.findAllByChatRoomId(chatRoomId)
                        .orElse(null);

        // ResponseMemberStatus 리스트를 생성하여 로그인 상태와 멤버 정보를 담기
        List<ResponseMemberStatus> responseList = new ArrayList<>();

        for (ChatRoomUser chatRoomUser : chatRoomUsers) {
            String nickname = chatRoomUser.getMember().getNickname(); // 해당 멤버 정보
            ChatRoomUserStatus userLoginStatus = chatRoomUser.getUserLoginStatus(); // 해당 멤버의 로그인 상태

            // ResponseMemberStatus 객체 생성 후 리스트에 추가
            ResponseMemberStatus responseMemberStatus = new ResponseMemberStatus(nickname, userLoginStatus);
            responseList.add(responseMemberStatus);
        }

        return responseList;
    }
}
