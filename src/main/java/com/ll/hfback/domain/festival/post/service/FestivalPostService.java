package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.api.repository.KopisRepository;
import com.ll.hfback.domain.festival.comment.entity.FestivalComment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Transactional(readOnly=true)
@RequiredArgsConstructor
@Service
public class FestivalPostService {
    private final KopisRepository kopisRepository;

    @Transactional
    public void modifyComment(FestivalComment festivalComment, String content) {
        festivalComment.setContent(content);
    }

    public Optional<KopisFesEntity> findById(Long id) {
        return kopisRepository.findById(id);
    }

    public List<KopisFesEntity> findAll() {
        return kopisRepository.findAll();
    }

    public List<KopisFesEntity> searchByName(String keyword) {
        return kopisRepository.findByFestivalNameContaining(keyword);
    }
}
