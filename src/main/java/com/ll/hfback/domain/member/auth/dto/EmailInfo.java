package com.ll.hfback.domain.member.auth.dto;

import java.time.LocalDateTime;

public record EmailInfo(
    String maskedEmail,
    LocalDateTime createdAt  // 가입일
) {}
