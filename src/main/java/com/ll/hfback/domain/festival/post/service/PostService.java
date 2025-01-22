package com.ll.hfback.domain.festival.post.service;

import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.comment.entity.Comment;
import com.ll.hfback.domain.festival.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional(readOnly=true)
@RequiredArgsConstructor
@Service
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void modifyComment(Comment comment, String content) {
        comment.setContent(content);
    }

    // 모든 게시글 조회
    public List<KopisFesEntity> findAll() {
        return postRepository.findAll();
    }

    // 키워드로 게시글 조회
    public List<KopisFesEntity> searchByKeyword(String keyword) {
        return postRepository.findByFestivalName(keyword);
    }

    // 게시글ID로 상세 조회
    public KopisFesEntity searchByFestivalId(String festivalId) {
        return postRepository.findByFestivalId(festivalId);
    }

    // 공연/축제(KOPIS/APIS) 타입으로 게시글 조회
    public List<KopisFesEntity> searchByInputType(String type) {
        return postRepository.findByInputType(type);
    }
}
