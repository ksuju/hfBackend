package com.ll.hfback.domain.member.member.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressResponse {
    private String roadAddr;
    private String jibunAddr;
    private String zipNo;
}
