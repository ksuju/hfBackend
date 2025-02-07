package com.ll.hfback.domain.festival.search.controller;

import com.ll.hfback.domain.festival.post.dto.PostDto;
import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import com.ll.hfback.domain.festival.search.service.PostDocService;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public Page<MainPostDoc> searchPosts(@RequestParam("keyword") String keyword, Pageable pageable) {
        return postDocService.searchByKeyword(keyword, pageable);
    }

}
