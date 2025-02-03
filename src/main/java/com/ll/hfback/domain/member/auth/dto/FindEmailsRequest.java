package com.ll.hfback.domain.member.auth.dto;

import jakarta.validation.constraints.NotBlank;

public record FindEmailsRequest(
    @NotBlank String phoneNumber
) {}
