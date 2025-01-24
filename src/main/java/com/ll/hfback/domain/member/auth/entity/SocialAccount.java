package com.ll.hfback.domain.member.auth.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SocialAccount extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @Column(nullable = false, length = 50)
  private String provider;  // 소셜 로그인 제공자

  @Column(length = 100)
  private String providerId;  // 소셜 자체 ID

  @JsonIgnore
  @Column(columnDefinition = "TEXT")
  private String refreshToken;


  // 관련 메서드

}
