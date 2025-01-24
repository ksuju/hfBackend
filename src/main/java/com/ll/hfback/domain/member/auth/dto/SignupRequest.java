package com.ll.hfback.domain.member.auth.dto;

import com.ll.hfback.domain.member.member.entity.Member.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String email;
    @NotBlank
    private String password;
    @NotBlank
    private String nickname;
    @NotNull
    private Gender gender;
    private boolean mkAlarm;
    @NotBlank
    private String phoneNumber;
}
