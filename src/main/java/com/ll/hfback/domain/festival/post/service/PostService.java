package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.post.dto.DetailPostDto;
import com.ll.hfback.domain.festival.post.dto.PostDto;

import java.util.List;

public interface PostService {
    // 모든 게시글 조회
    List<PostDto> findAll();

    // 키워드로 게시글 조회
    List<PostDto> searchByKeyword(String keyword);

    // 게시글ID로 상세 조회
    DetailPostDto searchByFestivalId(String festivalId);

    // 공연 장르별 게시글 조회
    List<PostDto> searchByGenrenm(String genre);
}
