package com.ll.hfback.domain.group.chatRoom.dto;

import com.ll.hfback.domain.group.chatRoom.converter.StringDoubleListConverter;
import com.ll.hfback.domain.group.chatRoom.converter.StringListConverter;
import jakarta.persistence.Convert;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class DetailChatRoomDto {
    private Long memberId;
    private Long chatRoomId;
    private String memberNickName;
    private String roomTitle;
    private String roomContent;
    @Convert(converter = StringDoubleListConverter.class)
    private List<List<String>> joinMemberIdNickNameList;
    @Convert(converter = StringDoubleListConverter.class)
    private List<List<String>> waitingMemberIdNickNameList;
    private Long roomMemberLimit;
    private int joinMemberNum;
    private int waitMemberNum;
    private LocalDateTime createDate;
}
