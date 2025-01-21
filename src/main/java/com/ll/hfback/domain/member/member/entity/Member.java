package com.ll.hfback.domain.member.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
public class Member extends BaseEntity {
  @Column(unique = true)
  private String username;
  @JsonIgnore
  private String password;
  @JsonIgnore
  private String refreshToken;
}
