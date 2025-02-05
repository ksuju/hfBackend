package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.post.dto.DetailPostDto;
import com.ll.hfback.domain.festival.post.dto.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostService {
    // 모든 게시글 조회
    Page<PostDto> findAll(Pageable pageable);

    // 키워드로 게시글 조회
    Page<PostDto> searchByKeyword(String keyword, Pageable pageable);

    // 게시글ID로 상세 조회
    DetailPostDto searchByFestivalId(String festivalId);

    // 장르별 게시글 조회(축제, 연극, 무용(서양/한국무용), 대중무용, 서양음악(클래식),
    // 한국음악(국악), 대중음악, 복합, 서커스/마술, 뮤지컬)
    Page<PostDto> searchByGenreOrAll(String genre, Pageable pageable);

    // 지역 기준으로 게시글 조회
    List<PostDto> searchByFestivalArea(String area, Integer count);
}
