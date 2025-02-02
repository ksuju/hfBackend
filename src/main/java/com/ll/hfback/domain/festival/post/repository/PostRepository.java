package com.ll.hfback.domain.festival.post.repository;

import com.ll.hfback.domain.festival.post.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 모든 게시글 조회
    Page<Post> findAll(Pageable pageable);

    // 키워드로 게시글 조회
    Page<Post> findByFestivalNameContaining(String keyword, Pageable pageable);

    // 게시글ID로 상세 조회
    Post findByFestivalId(String festivalId);

    // 공연 장르별 게시글 조회
    @Query("SELECT p FROM Post p WHERE :genre IS NULL OR :genre = '' OR p.genrenm = :genre")
    Page<Post> findByGenreOrAll(@Param("genre") String genre, Pageable pageable);

    // 사용자 위치순으로 게시글 조회
    List<Post> findByFestivalAreaContaining(String area);
}
