package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import com.ll.hfback.domain.festival.post.entity.FestivalPost;
import com.ll.hfback.domain.festival.post.repository.FestivalPostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly=true)
@RequiredArgsConstructor
@Service
public class FestivalPostService {
    private final FestivalPostRepository festivalPostRepository;

    @Transactional
    public void modifyComment(FestivalComment festivalComment, String content) {
        festivalComment.setContent(content);
    }

    public Optional<FestivalPost> findById(Long id) {
        return festivalPostRepository.findById(id);
    }

    public List<FestivalPost> findAll() {
        return festivalPostRepository.findAll();
    }

    // 관리자 권한 필요
    @Transactional
    public void delete(Long id) {
        this.festivalPostRepository.deleteById(id);
    }
}
