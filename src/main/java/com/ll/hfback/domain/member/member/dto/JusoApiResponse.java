package com.ll.hfback.domain.member.member.dto;

import lombok.Data;

import java.util.List;

@Data
public class JusoApiResponse {

  private Results results;


  @Data
  public static class Results {
    private List<Juso> juso;
  }

  @Data
  public static class Juso {
    private String roadAddr;
    private String jibunAddr;
    private String zipNo;

  }

}
