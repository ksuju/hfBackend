package com.ll.hfback.domain.group.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.ll.hfback.domain.group.chat.response
 * fileName       : ResponseMessageCount
 * author         : sungjun
 * date           : 2025-02-06
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-06        kyd54       최초 생성
 */
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseMessageCount {
    @JsonProperty("messageId")
    private Long messageId;

    @JsonProperty("count")
    private Long count;
}
