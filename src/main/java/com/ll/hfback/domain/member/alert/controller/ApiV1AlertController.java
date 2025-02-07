package com.ll.hfback.domain.member.alert.controller;

import com.ll.hfback.domain.member.alert.dto.AlertRequest;
import com.ll.hfback.domain.member.alert.dto.AlertResponse;
import com.ll.hfback.domain.member.alert.service.AlertService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.service.MemberService;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import com.ll.hfback.global.rsData.RsData;
import com.ll.hfback.global.webMvc.LoginUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/alerts")
public class ApiV1AlertController {

    private final AlertService alertService;
    private final MemberService memberService;

    // ALERT01 : 알림 생성 (관리자)
    @PostMapping
    public RsData<Void> createAlert(@RequestBody AlertRequest request) {
        Member receiver = memberService.findById(request.getMemberId())
                .orElseThrow(() -> new ServiceException(ErrorCode.MEMBER_NOT_FOUND));

        alertService.send(receiver.getId(), request.getType(), request.getNavigationData(), receiver.getNickname(), "다함께 차차차");
        return new RsData<>("200", "알림이 전송되었습니다.");
    }


    // ALERT02 : 알림 단건 확인
    @PatchMapping("/{alertId}")
    public RsData<Void> readAlert(@PathVariable Long alertId, @LoginUser Member loginUser) {
        alertService.readAlert(alertId, loginUser.getId());
        return new RsData<>("200", "알림을 확인하였습니다.");
    }


    public record readAlertRequest(List<Long> alertIds) {}

    // ALERT03 : 알림 다건 확인
    @PatchMapping
    public RsData<List<Long>> readAlerts(
            @RequestBody readAlertRequest request, @LoginUser Member loginUser
        ) {
        alertService.readMultipleAlerts(request.alertIds, loginUser.getId());
        return new RsData<>("200", "알림을 대량으로 확인하였습니다.", request.alertIds);
    }


    // ALERT04 : 회원별 알림 목록
    @GetMapping
    public RsData<Page<AlertResponse>> getMemberAlerts(
            @LoginUser Member loginUser,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
        ) {
        Page<AlertResponse> alertResponses = alertService.getMemberAlerts(loginUser.getId(), page, size);
        return new RsData<>("200", "알림 목록을 조회하였습니다.", alertResponses);
    }

}
