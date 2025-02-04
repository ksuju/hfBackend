package com.ll.hfback.global.websocket;

import com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus;
import com.ll.hfback.domain.group.chat.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.stereotype.Component;

/**
 * packageName    : com.ll.hfback.global.websocket
 * fileName       : StompHandler
 * author         : sungjun
 * date           : 2025-02-03
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-03        kyd54       최초 생성
 */
@Component
@RequiredArgsConstructor
public class ChatMessageHandler implements ChannelInterceptor {

    private final ChatRoomUserRepository chatRoomUserRepository;
    private final Logger logger = LoggerFactory.getLogger(ChatMessageHandler.class.getName());

    // 전역변수설정
    private Long saveChatRoomId = null;
    private Long saveMemberId = null;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        logger.info("STOMP 메시지 타입: {}", accessor.getCommand());

        // 연결 요청(Connect) 또는 메시지 전송 시 특정 헤더 확인
        if (accessor.getMessageType() == SimpMessageType.HEARTBEAT) {
            return message;  // heartbeat 메시지는 그대로 통과
        }

        // 로그인, 로그아웃 상태 변경
        if (accessor.getCommand() == StompCommand.CONNECT) {
            logger.info("새로운 STOMP 연결 요청");
            // 채팅방, 멤버 정보 가져오기
            Long chatRoomId = Long.valueOf(accessor.getFirstNativeHeader("chatRoomId"));
            Long memberId = Long.valueOf(accessor.getFirstNativeHeader("memberId"));
            
            // 전역변수에 저장
            saveChatRoomId = chatRoomId;
            saveMemberId = memberId;

            chatRoomUserRepository.findByChatRoomIdAndMemberId(chatRoomId, memberId)
                    .ifPresentOrElse(user -> {
                // chatRoomUser가 존재하면 처리
                logger.info("채팅방 유저 찾음: {}", user);
                if (user.getUserLoginStatus() != ChatRoomUserStatus.LOGIN) {    // 로그인 상태가 변경되어야 할 때만 처리
                    user.setUserLoginStatus(ChatRoomUserStatus.LOGIN);  // 로그인 상태로 변경
                    chatRoomUserRepository.save(user);  // 저장
                }
            }, () -> {
                // chatRoomUser가 없으면 로그 남기기
                logger.error("채팅방 유저 정보 없음: chatRoomId={}, memberId={}", chatRoomId, memberId);
            });

        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {
            logger.info("STOMP 연결 종료");

            chatRoomUserRepository.findByChatRoomIdAndMemberId(saveChatRoomId, saveMemberId)
                    .ifPresentOrElse(user -> {
                        // chatRoomUser가 존재하면 처리
                        logger.info("채팅방 유저 찾음: {}", user);
                        if (user.getUserLoginStatus() != ChatRoomUserStatus.LOGOUT) {    // 로그아웃 상태가 변경되어야 할 때만 처리
                            user.setUserLoginStatus(ChatRoomUserStatus.LOGOUT);  // 로그아웃 상태로 변경
                            chatRoomUserRepository.save(user);  // 저장
                        }
                    }, () -> {
                        // chatRoomUser가 없으면 로그 남기기
                        logger.error("채팅방 유저 정보 없음: chatRoomId={}, memberId={}", saveChatRoomId, saveMemberId);
                    });
        }

        return message;
    }
}