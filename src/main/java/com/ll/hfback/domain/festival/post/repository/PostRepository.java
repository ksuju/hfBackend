package com.ll.hfback.domain.festival.post.repository;

import com.ll.hfback.domain.festival.post.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {
    // 키워드로 게시글 조회
    List<Post> findByFestivalNameContaining(String keyword);
    // 게시글ID로 상세 조회
    Post findByFestivalId(String festivalId);
    // 공연/축제(KOPIS/APIS) 타입으로 게시글 조회
    List<Post> findByInputType(String type);
}
