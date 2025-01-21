package com.ll.hfback.domain.user.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ll.hfback.global.jpa.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class User extends BaseEntity {
    @Column(unique = true)
    private String username;
    @JsonIgnore
    private String password;
//    @JsonIgnore
//    private String refreshToken;
}
