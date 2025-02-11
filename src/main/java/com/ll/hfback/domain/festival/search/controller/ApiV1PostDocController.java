package com.ll.hfback.domain.festival.search.controller;

import com.ll.hfback.domain.festival.search.dto.ResponseFestivalSearch;
import com.ll.hfback.domain.festival.search.service.PostDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostDocController {
    private final PostDocService postDocService;

    @GetMapping("/search/all")
    public Page<ResponseFestivalSearch> getAllPosts(@ParameterObject Pageable pageable) {
        return postDocService.findAll(pageable);
    }

    @GetMapping("/search/key")
    public Page<ResponseFestivalSearch> searchPostName(
            @RequestParam(value = "genre", defaultValue = "") String genre,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "where", defaultValue = "") String where,
            Pageable pageable) {
        return postDocService.searchByKeyword(genre, keyword, where, pageable);
    }

    @GetMapping("search/main1")
    public List<ResponseFestivalSearch> getSubBanner1Search(@RequestParam(value = "area", defaultValue = "서울") String area) {
        log.debug(area);
        if (area.equals("null")) {
            area = "서울";
        }
        return postDocService.searchSubBannerUserLocationMeetingTop10(area);
    }

    @GetMapping("search/main2")
    public List<ResponseFestivalSearch> getSubBanner2Search() {
        return postDocService.getOngoingFestivals();
    }

    @GetMapping("search/main3")
    public List<ResponseFestivalSearch> getSubBanner3Search() {
        return postDocService.getSoonFestivals();
    }
}
