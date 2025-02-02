package com.ll.hfback.domain.member.member.dto;

import jakarta.validation.constraints.NotBlank;

public record AddPasswordRequest(
    @NotBlank String password
) {}
