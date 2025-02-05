package com.ll.hfback.domain.group.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDto {
    private Long memberId;
    private Long chatRoomId;
    private String roomTitle;
    private String roomContent;
    private String festivalName;
    private Long roomMemberLimit;
    private int joinMemberNum;
    private LocalDateTime createDate;
}
