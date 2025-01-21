package com.ll.hfback.domain.festival.post.dto;

import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import lombok.Getter;

@Getter
public class FestivalPostDto {
    private Long festival_id;

    public FestivalPostDto(FestivalPost festivalPost) {
        this.festival_id = festivalPost.getFestival_id();
    }
}
