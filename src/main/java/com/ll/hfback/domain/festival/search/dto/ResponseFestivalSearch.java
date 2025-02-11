package com.ll.hfback.domain.festival.search.dto;

import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import lombok.*;

import java.time.LocalDate;

@Getter @Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFestivalSearch {

    private String festivalName;
    private String festivalArea;
    private LocalDate festivalStartDate;
    private LocalDate festivalEndDate;
    private String festivalUrl;

    public static ResponseFestivalSearch convertToDTO(MainPostDoc mainPostDoc) {
        return ResponseFestivalSearch.builder()
                .festivalName(mainPostDoc.getFestivalName())
                .festivalArea(mainPostDoc.getFestivalArea())
                .festivalStartDate(mainPostDoc.getFestivalStartDate())
                .festivalEndDate(mainPostDoc.getFestivalEndDate())
                .festivalUrl(mainPostDoc.getFestivalUrl())
                .build();
    }
}
