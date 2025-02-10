package com.ll.hfback.domain.member.alert.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hfback.domain.member.alert.dto.AlertResponse;
import com.ll.hfback.domain.member.alert.entity.Alert;
import com.ll.hfback.domain.member.alert.enums.AlertType;
import com.ll.hfback.domain.member.alert.repository.AlertRepository;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.repository.MemberRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AlertService {

    private final AlertRepository alertRepository;
    private final MemberRepository memberRepository;
    private final SimpMessagingTemplate simpMessagingTemplate;
    private final ObjectMapper objectMapper;


    // === 알림 전송 ===
    @Transactional
    public void send(Long memberId, AlertType type, Map<String, Object> navigationData, String... args) {
        try {
            String navDataJson = objectMapper.writeValueAsString(navigationData);

            Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

            Alert alert = Alert.builder()
                .member(member)
                .content(type.formatMessage(args))
                .domain(type.getDomain())
                .alertTypeCode(type.name())
                .navigationData(navDataJson)
                .isRead(false)
                .build();

            Alert savedAlert = alertRepository.save(alert);
            _sendWebSocketMessage(savedAlert);

        } catch (JsonProcessingException e) {
            throw new ServiceException(ErrorCode.MAP_TO_JSON_FAILED);
        }
    }

    private void _sendWebSocketMessage(Alert alert) {
        AlertResponse response = AlertResponse.of(alert);

        simpMessagingTemplate.convertAndSendToUser(   // 실시간 토스트 메시지
            alert.getMember().getId().toString(),
            "/queue/toast-alerts",
            response
        );

        simpMessagingTemplate.convertAndSendToUser(     // 알림 목록 추가
            alert.getMember().getId().toString(),
            "/queue/alerts",
            response
        );

        System.out.println("Sending WebSocket message to user: " + alert.getMember().getId());
    }

    // === 알림 읽기 (단건) ===
    @Transactional
    public void readAlert(Long alertId, Long receiverId) {
        Alert alert = alertRepository.findById(alertId)
            .orElseThrow(() -> new ServiceException(ErrorCode.ALERT_NOT_FOUND));

        if (!alert.getMember().getId().equals(receiverId)) {
            throw new ServiceException(ErrorCode.NOT_AUTHORIZED);
        }

        alert.readAlert();
    }

    // === 알림 읽기 (다건) ===
    @Transactional
    public void readMultipleAlerts(List<Long> alertIds, Long receiverId) {
        if (alertIds == null || alertIds.isEmpty()) {
            throw new ServiceException(ErrorCode.ALERT_NOT_FOUND);
        }

        List<Alert> alerts = alertRepository.findAllById(alertIds);
        if (alerts.size() != alertIds.size() || alerts.isEmpty()) {
            throw new ServiceException(ErrorCode.ALERT_NOT_FOUND);
        }

        if (alerts.stream().anyMatch(alert -> !alert.getMember().getId().equals(receiverId))) {
            throw new ServiceException(ErrorCode.NOT_AUTHORIZED);
        }

        alerts.forEach(Alert::readAlert);
    }

    // === 해당 회원의 전체 알림 목록 ===
    public Page<AlertResponse> getMemberAlerts(Long memberId, int page, int size) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page, size, Sort.by("createDate").descending());
        return alertRepository.findByMemberId(member.getId(), pageable)
            .map(AlertResponse::of);
    }
}
