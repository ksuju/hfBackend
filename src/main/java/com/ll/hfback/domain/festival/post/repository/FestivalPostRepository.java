package com.ll.hfback.domain.festival.post.repository;

import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FestivalPostRepository extends JpaRepository<FestivalPost, Long> {
}
