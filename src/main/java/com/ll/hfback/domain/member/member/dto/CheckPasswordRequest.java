package com.ll.hfback.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;

public record CheckPasswordRequest(
    @NotBlank
    String password
) {}
