package com.ll.hfback.domain.group.chat.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.ll.hfback.domain.group.chat.enums.ChatRoomUserStatus;
import lombok.AllArgsConstructor;
import lombok.Setter;

/**
 * packageName    : com.ll.hfback.domain.group.chat.response
 * fileName       : ResponseMemberStatus
 * author         : sungjun
 * date           : 2025-02-04
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-02-04        kyd54       최초 생성
 */
@Setter
@AllArgsConstructor
public class ResponseMemberStatus {
    @JsonProperty("nickname")
    private String nickname;

    @JsonProperty("userLoginStatus")
    private ChatRoomUserStatus userLoginStatus;
}
