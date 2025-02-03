package com.ll.hfback.domain.member.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record SetNewPasswordRequest(
    @NotBlank
    @Email
    String email,

    @NotBlank
    String newPassword
) {}
