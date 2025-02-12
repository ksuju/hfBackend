package com.ll.hfback.domain.festival.search.document;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@Builder
@Setting(settingPath = "/elasticsearch/settings.json")
@Mapping(mappingPath = "/elasticsearch/mappings.json")
@Document(indexName = "festival")
@AllArgsConstructor
@NoArgsConstructor
public class MainPostDoc {
    @Id
    private String festivalId;

    @Field(name = "festival_name")
    private String festivalName; // 축제 title

    @Field(name = "festival_area")
    private String festivalArea; // 축제 지역

    @Field(name = "festival_start_date", type = FieldType.Date, pattern = "yyyy.MM.dd")
    private LocalDate festivalStartDate; // 축제 시작일

    @Field(name = "festival_end_date", type = FieldType.Date, pattern = "yyyy.MM.dd")
    private LocalDate festivalEndDate; // 축제 종료일

    @Field(name = "festival_url")
    private String festivalUrl; // 축제 사진

    @Field(name = "genrenm")
    private String festivalGenre;

    @Field(name = "chatroom_count")
    private int chatroomCount;

    @Field(name = "festival_state")
    private String festivalState;
}
