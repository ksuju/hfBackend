package com.ll.hfback.domain.member.alert.dto;

import com.ll.hfback.domain.member.alert.enums.AlertType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlertRequest {
  private Long memberId;
  private AlertType type;
  private Map<String, Object> navigationData;
}
