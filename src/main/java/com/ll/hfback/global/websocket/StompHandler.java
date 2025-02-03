package com.ll.hfback.global.websocket;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class StompHandler implements ChannelInterceptor {

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        log.info("STOMP 메시지 타입: {}", accessor.getCommand());

        // 연결 요청(Connect) 또는 메시지 전송 시 특정 헤더 확인
        if (accessor.getMessageType() == SimpMessageType.HEARTBEAT) {
            return message;  // heartbeat 메시지는 그대로 통과
        }

        if (accessor.getCommand() == StompCommand.CONNECT) {
            log.info("새로운 STOMP 연결 요청");
        } else if (accessor.getCommand() == StompCommand.DISCONNECT) {
            log.info("STOMP 연결 종료");
        }

        return message;
    }
}