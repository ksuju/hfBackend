package com.ll.hfback.domain.festival.post.dto;

import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import lombok.Getter;
import java.time.LocalDateTime;

@Getter
public class FestivalPostDto {
    private final Long id;
    private final String festival_name;
    private final LocalDateTime festival_start_date;
    private final LocalDateTime festival_end_date;
    private final String festival_area;
    private final String festival_hall_name;
    private final boolean festival_status;
    private final String festival_url;
    private final boolean festival_state;
    private final String input_type;

    public FestivalPostDto(FestivalPost festivalPost) {
        this.id = festivalPost.getId();
        this.festival_name = festivalPost.getFestival_name();
        this.festival_start_date = festivalPost.getFestival_start_date();
        this.festival_end_date = festivalPost.getFestival_end_date();
        this.festival_area = festivalPost.getFestival_area();
        this.festival_hall_name = festivalPost.getFestival_hall_name();
        this.festival_status = festivalPost.isFestival_status();
        this.festival_url = festivalPost.getFestival_url();
        this.festival_state = festivalPost.isFestival_state();
        this.input_type = festivalPost.getInput_type();
    }
}
