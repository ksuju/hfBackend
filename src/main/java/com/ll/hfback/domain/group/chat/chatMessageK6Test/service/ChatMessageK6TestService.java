package com.ll.hfback.domain.group.chat.chatMessageK6Test.service;

import com.ll.hfback.domain.group.chat.entity.ChatMessage;
import com.ll.hfback.domain.group.chat.entity.QChatMessage;
import com.ll.hfback.domain.group.chat.repository.ChatMessageRepository;
import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.search.document.ChatMessageDoc;
import com.ll.hfback.domain.group.chat.search.repository.ChatMessageDocRepository;
import com.querydsl.core.BooleanBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.ll.hfback.domain.group.chat.chatMessageK6Test.service
 * fileName       : ChatMessageK6test
 * author         : sungjun
 * date           : 2025-02-11
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-11        kyd54       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageK6TestService {

    private final ChatMessageRepository chatMessageRepository;
    private  final ChatMessageDocRepository chatMessageDocRepository;

    // 고정된 페이지 크기 10으로 Pageable 객체를 생성 (메시지 불러올 때 사용할 커스텀 페이징)
    public Pageable customPaging(int page) {
        try {
            // 내림차순 정렬 (createdAt 기준)
            Sort sort = Sort.by(Sort.Order.desc("createDate"));
            // 페이지 번호와 크기, 정렬을 포함하여 Pageable 객체 생성
            return PageRequest.of(page, 10, sort);
        } catch (Exception e) {
            throw e;
        }
    }

    // mysql 검색
    public Page<ResponseMessage> searchMessagesMysql(Long chatRoomId,
                                                int page,
                                                MessageSearchKeywordsRequest messageSearchKeywordsRequest) {
        try {

            QChatMessage qChatMessage = QChatMessage.chatMessage; // QueryDSL 메타 모델 객체

            BooleanBuilder builder = new BooleanBuilder();

            // 검색어(keyword)가 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest != null && messageSearchKeywordsRequest.getKeyword() != null) {
                String keyword = messageSearchKeywordsRequest.getKeyword();
                builder.and(qChatMessage.chatMessageContent.containsIgnoreCase(keyword)); // 대소문자 구분 없이 검색
            }

            // 닉네임(nickname)이 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest != null && messageSearchKeywordsRequest.getNickname() != null) {
                String nickname = messageSearchKeywordsRequest.getNickname();
                builder.and(qChatMessage.nickname.containsIgnoreCase(nickname)); // 대소문자 구분 없이 검색
            }
            // chatRoomId에 맞는 메시지 필터링
            builder.and(qChatMessage.chatRoom.id.eq(chatRoomId));

            // 페이지 처리와 함께 검색된 메시지 조회
            Page<ChatMessage> searchMessages = chatMessageRepository.findAll(builder, customPaging(page));

            // ChatMessage -> RequestMessage 변환
            return searchMessages.map(chatMessage ->
                    new ResponseMessage(chatMessage.getNickname(),
                            chatMessage.getEmail(),
                            chatMessage.getChatMessageContent(),
                            chatMessage.getCreateDate(),
                            chatMessage.getId()));
        } catch (IllegalArgumentException e) {
            // 검증 실패 예외 (채팅방 없음, 참여하지 않은 사용자)
            throw  e;
        } catch (Exception e) {
            throw e;
        }
    }


    // 엘라스틱서치
    public Page<ResponseMessage> searchMessagesEks(Long chatRoomId,
                                                int page,
                                                MessageSearchKeywordsRequest messageSearchKeywordsRequest) {
        try {
            // 검색어(keyword)가 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest != null && messageSearchKeywordsRequest.getKeyword() != null) {

                String keyword = messageSearchKeywordsRequest.getKeyword();

                Page<ChatMessageDoc> searchMessages = chatMessageDocRepository.findByChatRoomIdAndChatMessageContentContaining(chatRoomId, keyword, customPaging(page));
                // ChatMessage -> ResponseMessage 변환
                return searchMessages.map(chatMessage ->
                        new ResponseMessage(chatMessage.getNickname(),
                                chatMessage.getEmail(),
                                chatMessage.getChatMessageContent(),
                                chatMessage.getCreateDate(),
                                chatMessage.getChatMessageId()));
            }

            // 닉네임(nickname)이 있으면 해당 조건 추가
            if (messageSearchKeywordsRequest != null && messageSearchKeywordsRequest.getNickname() != null) {
                String nickname = messageSearchKeywordsRequest.getNickname();

                Page<ChatMessageDoc> searchMessages = chatMessageDocRepository.findByChatRoomIdAndNickname(chatRoomId, nickname, customPaging(page));

                // ChatMessage -> ResponseMessage 변환
                return searchMessages.map(chatMessage ->
                        new ResponseMessage(chatMessage.getNickname(),
                                chatMessage.getEmail(),
                                chatMessage.getChatMessageContent(),
                                chatMessage.getCreateDate(),
                                chatMessage.getChatMessageId()));
            }

            // 검색 조건이 없는 경우 해당 채팅방의 모든 메시지 조회
            Page<ChatMessageDoc> searchMessages = chatMessageDocRepository
                    .findByChatRoomId(chatRoomId, customPaging(page));

            // ChatMessage -> ResponseMessage 변환
            return searchMessages.map(chatMessage ->
                    new ResponseMessage(chatMessage.getNickname(),
                            chatMessage.getEmail(),
                            chatMessage.getChatMessageContent(),
                            chatMessage.getCreateDate(),
                            chatMessage.getChatMessageId()));
        } catch (Exception e) {
            throw e;
        }
    }
}
