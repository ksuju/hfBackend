package com.ll.hfback.domain.group.chat.search.serviceImpl;

import com.ll.hfback.domain.group.chat.request.MessageSearchKeywordsRequest;
import com.ll.hfback.domain.group.chat.response.ResponseMessage;
import com.ll.hfback.domain.group.chat.search.document.ChatMessageDoc;
import com.ll.hfback.domain.group.chat.search.repository.ChatMessageDocRepository;
import com.ll.hfback.domain.group.chat.search.service.ChatMessageDocService;
import com.ll.hfback.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * packageName    : com.ll.hfback.domain.group.chat.search.serviceImpl
 * fileName       : ChatMessageDocServiceImpl
 * author         : sungjun
 * date           : 2025-02-10
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-10        kyd54       최초 생성
 */
@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageDocServiceImpl implements ChatMessageDocService {
    private final ChatMessageDocRepository chatMessageDocRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageDocServiceImpl.class.getName());


    // 고정된 페이지 크기 10으로 Pageable 객체를 생성 (메시지 불러올 때 사용할 커스텀 페이징)
    public Pageable customPaging(int page) {
        try {
            // 내림차순 정렬 (createdAt 기준)
            Sort sort = Sort.by(Sort.Order.desc("createDate"));
            logger.info("커스텀 페이징 성공");

            // 페이지 번호와 크기, 정렬을 포함하여 Pageable 객체 생성
            return PageRequest.of(page, 10, sort);
        } catch (Exception e) {
            logger.error("커스텀 페이징 실패");
            throw e;
        }
    }

    public Page<ResponseMessage> searchMessages(Long chatRoomId,
                                                int page,
                                                MessageSearchKeywordsRequest messageSearchKeywordsRequest, Member loginUser) {
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
            logger.error("엘라스틱 서치를 이용한 채팅 검색에 실패했습니다.");
            throw e;
        }
    }
}
