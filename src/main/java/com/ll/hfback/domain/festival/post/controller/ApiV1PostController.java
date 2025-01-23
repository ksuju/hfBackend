package com.ll.hfback.domain.festival.post.controller;

import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/festivalPosts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    // 모든 게시글 조회
    @GetMapping("/all")
    public List<KopisFesEntity> getAllPosts() {
        List<KopisFesEntity> kopisFesEntities = postService.findAll();

        return kopisFesEntities;
    }

    // 키워드로 게시글 조회
    @GetMapping("/search")
    public List<KopisFesEntity> searchPosts(@RequestParam("keyword") String keyword) {
        List<KopisFesEntity> kopisFesEntities = postService.searchByKeyword(keyword);

        return kopisFesEntities;
    }

    // 게시글ID로 상세 조회
    @GetMapping("/{festivalId}")
    public KopisFesEntity getPost(@PathVariable("festivalId") String festivalId) {
        KopisFesEntity kopisFesEntity = postService.searchByFestivalId(festivalId);

        return kopisFesEntity;
    }

    // 공연/축제(KOPIS/APIS) 타입으로 게시글 조회
    @GetMapping("/get")
    public List<KopisFesEntity> getTypePosts(@RequestParam("type") String type) {
        List<KopisFesEntity> kopisFesEntities = postService.searchByInputType(type);

        return kopisFesEntities;
    }
}
