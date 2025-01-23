package com.ll.hfback.domain.festival.api.entity;

import com.ll.hfback.domain.group.room.entity.Room;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Setter
@Getter
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@SuperBuilder
@ToString(callSuper = true)
public class KopisFesEntity{

    @Id
    private String festivalId;

    private final String festivalName;
    private final String festivalStartDate;
    private final String festivalEndDate;
    private final String festivalArea;
    private final String festivalHallName;
    private final String festivalState;
    private final String festivalUrl;
    private final String inputType; //어느 카테고리로 들어왔는지.

    @CreatedDate
    @Getter//Setter는?
    private LocalDateTime createDate;

    @LastModifiedDate
    @Getter
    private LocalDateTime modifyDate;

    @OneToMany
    private List<Room> roomList;
}
