package com.ll.hfback.domain.member.alert.controller;

import com.ll.hfback.domain.member.alert.dto.AlertRequest;
import com.ll.hfback.domain.member.alert.service.AlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
public class ApiV1AlertController {

    private final AlertService alertService;

    // ALERT01 : 알림 생성
    @PostMapping
    public void createAlert(@RequestBody AlertRequest request) {
        // alertService.createAlert(request)
    }

    // ALERT02 : 알림 확인


    // ALERT03 : 회원별 알림 목록
    @GetMapping("/{memberId}")
    public void getMemberAlerts(@PathVariable Long memberId) {
        // alertService.getMemberAlerts(memberId);
    }

    // ALERT04 : 알림 링크 이동

}
