package com.ll.hfback.global.websocket;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

/**
 * packageName    : com.ll.hfback.global.websocket
 * fileName       : WebSocketConfig
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@Configuration
@EnableWebSocketMessageBroker   // STOMP 기반의 WebSocket 메시지 브로커 활성화
@RequiredArgsConstructor
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

//    private final ChatMessageHandler chatMessageHandler;

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws/chat")    // 클라이언트가 WebSocket 연결을 시작하는 엔드포인트 지정
                .setAllowedOrigins("*");    // CORS 허용할 경로 지정
                //.withSockJS();  // WebSocKet 지원되지 않는 환경에서 SockJS를 통해 실시간 통신을 구현
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic", "/queue", "/user");  // 메시지 브로커 활성화, 클라이언트가 설정한 경로를 구독하면 서버가 발행하는 메시지를 받을 수 있음
        registry.setApplicationDestinationPrefixes("/app"); // 클라이언트가 서버로 메시지를 보낼 때 사용할 경로의 prefix를 지정, ex) /app/message로 메시지를 보내면, 서버에서 해당 경로를 처리함
        registry.setUserDestinationPrefix("/user");
    }


//    @Override
//    public void configureClientInboundChannel(ChannelRegistration registration) {
//        registration.interceptors(chatMessageHandler);
//    }
}
