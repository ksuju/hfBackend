package com.ll.hfback.domain.festival.search.service;

import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import com.ll.hfback.domain.festival.search.dto.ResponseFestivalSearch;
import com.ll.hfback.domain.festival.search.repository.PostDocRepository;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostDocService {
    private final PostDocRepository postDocRepository;

    public Page<ResponseFestivalSearch> findAll(Pageable pageable) {
        try {
            return convertToDTO(postDocRepository.findAllPost(sortingPageAsc(pageable, "festival_name.keyword")));
        } catch (Exception e) {
            throw new ServiceException(ErrorCode.SEARCH_POST_ERROR);
        }
    }

    public Page<ResponseFestivalSearch> searchByKeyword(String genre, String keyword, String where, Pageable pageable) {

        boolean isWhere = where.isEmpty() || where.equals("all");  // 1개 라도 true > true

        Pageable sortingPage = sortingPageAsc(pageable, "festival_name.keyword");

        // 전체 검색 > genre X && where이 없거나 all && keword가 없을때
        if (genre.isEmpty() && isWhere && keyword.isEmpty()) {
            return findAll(sortingPage);
        }

        // 장르만 적용 > where이 없거나 all, && keyword가 없으면
        if (isWhere && keyword.isEmpty()) {
            log.debug("검색 장르: {}", genre);
            return convertToDTO(postDocRepository.findAllByFestivalGenre(genre, sortingPage));
        }

        // 장르가 없는 경우
        if (genre.isEmpty()) {
            log.debug("where: {}", where);
            log.debug("genre: {}", genre);
            return switch (where) {
                case "name" -> convertToDTO(postDocRepository.findByFestivalNameContaining(keyword, sortingPage));
                case "area" -> convertToDTO(postDocRepository.findByFestivalAreaContaining(keyword, sortingPage));
                default -> convertToDTO(postDocRepository.findByFestivalNameContainingOrFestivalAreaContaining(keyword, sortingPage));
            };
        }

        // 장르가 있는 경우
        return switch (where) {
            case "name" -> convertToDTO(postDocRepository.findByFestivalGenreAndFestivalNameContaining(genre, keyword, sortingPage));
            case "area" -> convertToDTO(postDocRepository.findByFestivalGenreAndFestivalAreaContaining(genre, keyword, sortingPage));
            default -> convertToDTO(postDocRepository.findByFestivalGenreAndFestivalNameContainingOrFestivalAreaContaining(genre, keyword, sortingPage));
        };
    }

    public List<ResponseFestivalSearch> searchSubBannerUserLocationMeetingTop5 (String area) {
        Pageable pageable = PageRequest.of(0, 5, Sort.by(Sort.Order.desc("chatroom_count")));

        return postDocRepository.findByFestivalAreaContaining(area.split(" ")[0], pageable)
                        .getContent()
                        .stream()
                        .map(ResponseFestivalSearch::convertToDTO)  // MainPostDoc -> ResponseFestivalSearch 변환
                        .collect(Collectors.toList());
    }

    public List<ResponseFestivalSearch> getOngoingFestivals() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by(Sort.Order.asc("festival_end_date")));

        return postDocRepository.findByFestivalStateAndFestivalEndDateGreaterThanOrderByFestivalEndDateAsc("공연중", pageable)
                .getContent()
                .stream()
                .map(ResponseFestivalSearch::convertToDTO)  // MainPostDoc -> ResponseFestivalSearch 변환
                .collect(Collectors.toList());
    }

    public List<ResponseFestivalSearch> getSoonFestivals() {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("festival_start_date").ascending());

        return postDocRepository.findUpcomingFestivals(pageable)
                .getContent()
                .stream()
                .map(ResponseFestivalSearch::convertToDTO)  // MainPostDoc -> ResponseFestivalSearch 변환
                .collect(Collectors.toList());
    }


    // 변환 코드
    private Pageable sortingPageAsc(Pageable pageable, String sortEntity) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.asc(sortEntity)));
    }

    private Pageable sortingPageDesc(Pageable pageable, String sortEntity) {
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(Sort.Order.desc(sortEntity)));
    }

    private Page<ResponseFestivalSearch> convertToDTO(Page<MainPostDoc> mainPostDoc) {
        return new PageImpl<>(
                mainPostDoc.getContent().stream()
                .filter(Objects::nonNull)
                .map(ResponseFestivalSearch::convertToDTO) // ResponseFestivalSearch에서 변환
                .toList(),
                mainPostDoc.getPageable(),
                mainPostDoc.getTotalElements()
        );
    }
}
