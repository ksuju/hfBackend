package com.ll.hfback.domain.festival.comment.service;

import com.ll.hfback.domain.festival.comment.repository.FestivalCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FestivalCommentService {
    final FestivalCommentRepository festivalCommentRepository;
}
