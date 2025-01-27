package com.ll.hfback.domain.festival.post.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Post {
    @Id
    @JacksonXmlProperty(localName = "mt20id")
    private String festivalId;

    @JacksonXmlProperty(localName = "prfnm")
    private String festivalName;

    @JacksonXmlProperty(localName = "prfpdfrom")
    private String festivalStartDate;

    @JacksonXmlProperty(localName = "prfpdto")
    private String festivalEndDate;

    @JacksonXmlProperty(localName = "area")
    private String festivalArea;

    @JacksonXmlProperty(localName = "fcltynm")
    private String festivalHallName;

    @JacksonXmlProperty(localName = "prfstate")
    private String festivalState;

    @JacksonXmlProperty(localName = "poster")
    private String festivalUrl;

    @JacksonXmlProperty(localName = "genrenm")
    private String genrenm;

    private LocalDateTime createDate;
    private LocalDateTime modifyDate;
    private String inputType;
}
