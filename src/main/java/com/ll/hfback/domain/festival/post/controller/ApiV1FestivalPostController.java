package com.ll.hfback.domain.festival.post.controller;

import com.ll.hfback.domain.festival.post.dto.FestivalPostDto;
import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import com.ll.hfback.domain.festival.post.service.FestivalPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/festivalPosts")
@RequiredArgsConstructor
public class ApiV1FestivalPostController {
    private final FestivalPostService festivalPostService;

    @GetMapping
    public List<FestivalPostDto> getFestivalPosts() {
        List<FestivalPost> festivalPosts = festivalPostService.findAll();

        List<FestivalPostDto> festivalPostDtoList = festivalPosts.stream()
                .map(FestivalPostDto::new)
                .toList();

        return festivalPostDtoList;
    }
}
