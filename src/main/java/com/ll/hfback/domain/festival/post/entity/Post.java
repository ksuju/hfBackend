package com.ll.hfback.domain.festival.post.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Post {
    @Id
    private String festivalId;

    private String festivalName;
    private String festivalStartDate;
    private String festivalEndDate;
    private String festivalArea;
    private String festivalHallName;
    private String festivalState;
    private String festivalUrl;
    private String inputType;
    private String genrenm;

    @CreatedDate
    @Getter//Setter는?
    private LocalDateTime createDate;

    @LastModifiedDate
    @Getter
    private LocalDateTime modifyDate;

}
