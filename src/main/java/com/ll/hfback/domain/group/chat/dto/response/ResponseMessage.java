package com.ll.hfback.domain.group.chat.dto.response;

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
public class ResponseMessage {
    private String nickname;    // fix: 추후 memberId로 수정
    private String content;
}
