package com.ll.hfback.domain.group.chatRoom.dto;

import com.ll.hfback.domain.group.chatRoom.converter.StringListConverter;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetailChatRoomDto {
    private Long chatRoomId;
    private String memberNickName;
    private String roomTitle;
    private String roomContent;
    @Convert(converter = StringListConverter.class)
    private List<String> joinMemberList;
    private Long roomMemberLimit;
    private int joinMemberNum;
}
