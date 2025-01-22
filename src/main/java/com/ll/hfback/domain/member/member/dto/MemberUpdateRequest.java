package com.ll.hfback.domain.member.member.dto;

import com.ll.hfback.domain.member.member.entity.Member.Gender;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class MemberUpdateRequest {

    @NotBlank
    private String email;
    @NotBlank
    private String nickname;
    @NotBlank
    private String password;
    @NotBlank
    private String phoneNumber;
    @NotBlank
    private boolean mkAlarm;
    @NotBlank
    private Gender gender;

    private String profilePath;
    private String location;
    private LocalDate birthday;

}
