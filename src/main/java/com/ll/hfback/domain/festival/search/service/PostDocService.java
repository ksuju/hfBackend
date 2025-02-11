package com.ll.hfback.domain.festival.search.service;

import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import com.ll.hfback.domain.festival.search.repository.PostDocRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostDocService {
    private final PostDocRepository postDocRepository;

    public Page<MainPostDoc> findAll(Pageable pageable) {
        return postDocRepository.findAll(pageable);
    }

    public Page<MainPostDoc> searchByKeyword(String genre, String keyword, String where, Pageable pageable) {

        // 전체 검색
        if (genre.isEmpty() && where.isEmpty() && keyword.isEmpty()) {
            return findAll(pageable);
        }

        // 장르만 적용
        if (where.isEmpty() && keyword.isEmpty()) {
            return postDocRepository.findAllByFestivalGenre(genre, pageable);
        }

        // 장르가 없는 경우
        if (genre.isEmpty()) {
            log.debug("where: {}", where);
            return switch (where) {
                case "name" -> postDocRepository.findByFestivalNameContaining(keyword, pageable);
                case "area" -> postDocRepository.findByFestivalAreaContaining(keyword, pageable);
                default -> postDocRepository.findByFestivalNameContainingOrFestivalAreaContaining(keyword, pageable);
            };
        }

        // 장르가 있는 경우
        return switch (where) {
            case "name" -> postDocRepository.findByFestivalGenreAndFestivalNameContaining(genre, keyword, pageable);
            case "area" -> postDocRepository.findByFestivalGenreAndFestivalAreaContaining(genre, keyword, pageable);
            default -> postDocRepository.findByFestivalGenreAndFestivalNameContainingOrFestivalAreaContaining(genre, keyword, pageable);
        };
    }
}
