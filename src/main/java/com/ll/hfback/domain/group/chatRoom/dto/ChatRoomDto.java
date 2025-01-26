package com.ll.hfback.domain.group.chatRoom.dto;

import com.ll.hfback.domain.group.chatRoom.entity.ChatRoom;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
public class ChatRoomDto {
    private Long chatRoomId;
    private String roomTitle;
    private String roomContent;
    private Long roomMemberLimit;
    private int joinMemberNum;
}
