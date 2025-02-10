package com.ll.hfback.domain.festival.search.controller;

import com.ll.hfback.domain.festival.post.dto.PostDto;
import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import com.ll.hfback.domain.festival.search.service.PostDocService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Mapping;
import org.springframework.data.elasticsearch.annotations.Setting;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class ApiV1PostDocController {
    private final PostDocService postDocService;

    @GetMapping("/search/all")
    public Page<MainPostDoc> getAllPosts(@ParameterObject Pageable pageable) {
        return postDocService.findAll(pageable);
    }

    @GetMapping("/search/key")
    public Page<MainPostDoc> searchPostName(
            @RequestParam(value = "genre", defaultValue = "") String genre,
            @RequestParam(value = "keyword", defaultValue = "") String keyword,
            @RequestParam(value = "where", defaultValue = "") String where,
            Pageable pageable) {

        log.debug("genre: {}", genre);
        log.debug("keyword: {}", keyword);
        log.debug("where: {}", where);
        log.debug("pageable: {}", pageable);

        return postDocService.searchByKeyword(genre, keyword, where, pageable);
    }
}
