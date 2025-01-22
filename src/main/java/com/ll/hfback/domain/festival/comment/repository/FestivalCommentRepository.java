package com.ll.hfback.domain.festival.comment.repository;

import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FestivalCommentRepository extends JpaRepository<FestivalComment, Long> {
    List<FestivalComment> findByFestivalId(String festivalId);
    List<FestivalComment> findByMemberId(Long id);
}
