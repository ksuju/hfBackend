package com.ll.hfback.domain.member.member.dto;

import com.ll.hfback.domain.member.member.entity.Member.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MemberUpdateRequest {

    @NotBlank
    private String nickname;
    @NotNull
    private boolean mkAlarm;

    private Gender gender;
    private String location;
    private String phoneNumber;
    private LocalDate birthday;
}
