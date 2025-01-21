package com.ll.hfback.domain.festival.comment.service;

import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.festival.comment.repository.FestivalCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FestivalCommentService {
    final FestivalCommentRepository festivalCommentRepository;

    public List<FestivalComment> findByUserId(Long member_id) {
        return festivalCommentRepository.findByUserId(member_id);
    }
}
