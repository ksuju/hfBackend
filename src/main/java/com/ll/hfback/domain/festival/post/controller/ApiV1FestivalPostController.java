package com.ll.hfback.domain.festival.post.controller;

import com.ll.hfback.domain.festival.post.service.FestivalPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/festivalPosts")
@RequiredArgsConstructor
public class ApiV1FestivalPostController {
    private final FestivalPostService festivalPostService;
}
