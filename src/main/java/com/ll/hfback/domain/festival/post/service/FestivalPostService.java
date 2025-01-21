package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.festival.post.repository.FestivalPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Transactional(readOnly=true)
@RequiredArgsConstructor
@Service
public class FestivalPostService {
    private final FestivalPostRepository festivalPostRepository;

    @Transactional
    public void modifyComment(FestivalComment festivalComment, String content) {
        festivalComment.setContent(content);
    }

    @Transactional
    public void delete(Long id) {
        this.festivalPostRepository.deleteById(id);
    }
}
