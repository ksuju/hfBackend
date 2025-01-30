package com.ll.hfback.domain.member.auth.entity;

import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SocialAccount {

  @Id
  @Column(length = 50)
  @EqualsAndHashCode.Include
  private String providerId;  // 소셜제공자__소셜자체ID

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id", nullable = false)
  private Member member;

  @CreatedDate
  @Setter(AccessLevel.PRIVATE)
  private LocalDateTime createDate;


  // 관련 메서드

}
