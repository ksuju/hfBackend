package com.ll.hfback.domain.festival.comment.repository;

import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FestivalCommentRepository extends JpaRepository<FestivalComment, Long> {

}
