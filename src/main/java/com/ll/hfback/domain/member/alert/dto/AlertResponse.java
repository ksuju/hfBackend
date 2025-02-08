package com.ll.hfback.domain.member.alert.dto;

import com.ll.hfback.domain.member.alert.entity.Alert;
import com.ll.hfback.domain.member.alert.enums.NavigationType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertResponse {
  private Long id;
  private String content;
  private Boolean isRead;
  private LocalDateTime createDate;
  private NavigationType navigationType;
  private String navigationData;

  public static AlertResponse of(Alert alert) {
    return AlertResponse.builder()
        .id(alert.getId())
        .content(alert.getContent())
        .isRead(alert.getIsRead())
        .createDate(alert.getCreateDate())
        .navigationType(alert.getNavigationType())
        .navigationData(alert.getNavigationData())
        .build();
  }
}
