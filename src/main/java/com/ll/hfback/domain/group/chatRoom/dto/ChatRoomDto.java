package com.ll.hfback.domain.group.chatRoom.dto;

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
public class ChatRoomDto {
    private Long memberId;
    private Long chatRoomId;
    private String roomTitle;
    private String roomContent;
    private String festivalName;
    private Long roomMemberLimit;
    @Convert(converter = StringListConverter.class)
    private List<String> joinMemberIdList;
    private int joinMemberNum;
    @Convert(converter = StringListConverter.class)
    private List<String> waitingMemberIdList;
    private LocalDateTime createDate;
}
