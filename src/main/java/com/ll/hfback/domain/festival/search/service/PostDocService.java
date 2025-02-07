package com.ll.hfback.domain.festival.search.service;

import com.ll.hfback.domain.festival.post.dto.PostDto;
import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import com.ll.hfback.domain.festival.search.repository.PostDocRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostDocService {
    private final PostDocRepository postDocRepository;

    public Page<MainPostDoc> findAll(Pageable pageable) {
        return postDocRepository.findAll(pageable);
    }

    public Page<MainPostDoc> searchByKeyword(String keyword, Pageable pageable) {
        return postDocRepository.findByFestivalName(keyword, pageable);
    }
}
