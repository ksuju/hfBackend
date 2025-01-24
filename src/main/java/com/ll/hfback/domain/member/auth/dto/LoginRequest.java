package com.ll.hfback.domain.member.auth.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {
  @NotBlank
  private String email;
  @NotBlank
  private String password;
}
