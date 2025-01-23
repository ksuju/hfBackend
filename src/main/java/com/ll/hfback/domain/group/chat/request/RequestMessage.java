package com.ll.hfback.domain.group.chat.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Setter;

/**
 * packageName    : com.ll.hfback.domain.group.chat.request
 * fileName       : RequestMessage
 * author         : sungjun
 * date           : 2025-01-23
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-23        kyd54       최초 생성
 */
@Setter
public class RequestMessage {
    @JsonProperty("nickname")
    private String nickname;
    @JsonProperty("content")
    private String content;
}
