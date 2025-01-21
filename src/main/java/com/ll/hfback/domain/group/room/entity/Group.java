package com.ll.hfback.domain.group.room.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.*;

/**
 * packageName    : com.ll.hfback.domain.group.room.entity
 * fileName       : Group
 * author         : sungjun
 * date           : 2025-01-21
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-21        kyd54       최초 생성
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Group {
    @Id
    private Long groupId;

    // FK
    private Long festivalId;
    private Long memberId;
    //

    private String groupContent;
    private int groupMemberLimit;
    private int groupState;
}
