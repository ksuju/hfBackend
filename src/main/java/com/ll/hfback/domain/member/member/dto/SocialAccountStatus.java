package com.ll.hfback.domain.member.member.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
public class SocialAccountStatus {
  private LocalDateTime createDate;  // 연결 생성일
  private String email;
  private boolean active;     // 연결 상태
}
