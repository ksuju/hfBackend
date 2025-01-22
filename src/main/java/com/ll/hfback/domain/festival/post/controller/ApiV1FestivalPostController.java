package com.ll.hfback.domain.festival.post.controller;

import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.post.service.FestivalPostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/festivalPosts")
@RequiredArgsConstructor
public class ApiV1FestivalPostController {
    private final FestivalPostService festivalPostService;

    @GetMapping("/all")
    public List<KopisFesEntity> getAllFestivalPosts() {
        List<KopisFesEntity> kopisFesEntities = festivalPostService.findAll();

        return kopisFesEntities;
    }

    @GetMapping("/search")
    public List<KopisFesEntity> searchFestivalPosts(@RequestParam("keyword") String keyword) {
        List<KopisFesEntity> kopisFesEntities = festivalPostService.searchByName(keyword);

        return kopisFesEntities;
    }
}
