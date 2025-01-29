package com.ll.hfback.domain.group.chat.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * packageName    : com.ll.hfback.domain.group.chat.dto.response
 * fileName       : ResponseMessage
 * author         : sungjun
 * date           : 2025-01-22
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-22        kyd54       최초 생성
 */
@Getter
@Setter
@AllArgsConstructor
public class ResponseMessage {
    private Long memberId;
    private String content;
}
