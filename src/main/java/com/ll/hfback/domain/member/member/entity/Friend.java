package com.ll.hfback.domain.member.member.entity;

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
@Table(
    uniqueConstraints = {
        @UniqueConstraint(
            columnNames = {"requester_id", "receiver_id"}
        )
    }
)
public class Friend extends BaseEntity {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "requester_id")
  private Member requester;  // 친구 신청자


  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "receiver_id")
  private Member receiver;  // 친구 신청 상대


  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private FriendStatus status;
  public enum FriendStatus {
    PENDING,    // 대기 중
    ACCEPTED   // 수락됨
  }


  // 엔티티 메서드
  public void accept() {
    this.status = FriendStatus.ACCEPTED;
  }

  public boolean isAccepted() {
    return this.status == FriendStatus.ACCEPTED;
  }

  public boolean isPending() {
    return this.status == FriendStatus.PENDING;
  }

}
