package com.ll.hfback.domain.group.chatRoom.form;

import com.ll.hfback.domain.member.member.entity.Member;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateChatRoomForm {
    @NotEmpty
    @Size(max = 100)
    private String roomTitle;
    @NotEmpty
    @Size(max = 500)
    private String roomContent;
    private Member member;
    private Long roomMemberLimit; // 최소 10명 ~ 최대 100명 (스크롤 형식)
}
