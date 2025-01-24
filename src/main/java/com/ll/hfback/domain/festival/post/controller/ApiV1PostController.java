package com.ll.hfback.domain.festival.post.controller;

import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.festival.post.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostController {
    private final PostService postService;

    // 모든 게시글 조회
    @GetMapping("/all")
    public List<Post> getAllPosts() {
        List<Post> posts = postService.findAll();

        return posts;
    }

    // 키워드로 게시글 조회
    @GetMapping("/search")
    public List<Post> searchPosts(@RequestParam("keyword") String keyword) {
        List<Post> posts = postService.searchByKeyword(keyword);

        return posts;
    }

    // 게시글ID로 상세 조회
    @GetMapping("/{festival-id}")
    public Post getPost(@PathVariable("festival-id") String festivalId) {
        Post post = postService.searchByFestivalId(festivalId);

        return post;
    }

    // 공연/축제(KOPIS/APIS) 타입으로 게시글 조회
    @GetMapping("/get")
    public List<Post> getTypePosts(@RequestParam("type") String type) {
        List<Post> posts = postService.searchByInputType(type);

        return posts;
    }
}
