package com.ll.hfback.domain.festival.post.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DetailPostDto {
    private String festivalId;
    private String festivalName;
    private String festivalStartDate;
    private String festivalEndDate;
    private String festivalArea;
    private String festivalHallName;
    private String festivalUrl;
}
