package com.ll.hfback.domain.festival.search.repository;

import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import org.eclipse.jdt.internal.compiler.batch.Main;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface PostDocRepository extends ElasticsearchRepository<MainPostDoc, String> {

    @Query("""
        {
          "bool": {
            "must": [
              {
                "range": {
                  "festival_end_date": {
                    "gte": "now"
                  }
                }
              }
            ]
          }
        }
    """)
    Page<MainPostDoc> findAllPost(Pageable pageable);

    // 장르 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"genrenm\": \"?0\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]}}")
    Page<MainPostDoc> findAllByFestivalGenre(String genre, Pageable pageable);

    // 장르 상관X > 전체 검색
    @Query("{\"bool\": {" +
            "\"must\": [" +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]," +
            "\"should\": [" +
            "{\"wildcard\": {\"festival_name\": \"*?0*\"}}," +
            "{\"wildcard\": {\"festival_area\": \"*?0*\"}}" +
            "]," +
            "\"minimum_should_match\": 1" +
            "}}")
    Page<MainPostDoc> findByFestivalNameContainingOrFestivalAreaContaining(String keyword, Pageable pageable);

    // 장르 상관X > 제목 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"wildcard\": {\"festival_name\": \"*?0*\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]}}")
    Page<MainPostDoc> findByFestivalNameContaining(String keyword, Pageable pageable);

    // 장르 상관X > 지역 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"wildcard\": {\"festival_area\": \"*?0*\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]}}")
    Page<MainPostDoc> findByFestivalAreaContaining(String keyword, Pageable pageable);

    // 장르 포함 > 전체 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"genrenm\": \"?0\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}," +
            "{\"bool\": {\"should\": [" +
            "{\"wildcard\": {\"festival_name\": \"*?1*\"}}," +
            "{\"wildcard\": {\"festival_area\": \"*?1*\"}}" +
            "]}}" +
            "]}}")
    Page<MainPostDoc> findByFestivalGenreAndFestivalNameContainingOrFestivalAreaContaining(String genre, String keyword, Pageable pageable);

    // 장르 포함 > 제목 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"genrenm\": \"?0\"}}," +
            "{\"wildcard\": {\"festival_name\": \"*?1*\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]}}")
    Page<MainPostDoc> findByFestivalGenreAndFestivalNameContaining(String genre, String keyword, Pageable pageable);

    // 장르 포함 > 지역 검색
    @Query("{\"bool\": {\"must\": [" +
            "{\"match\": {\"genrenm\": \"?0\"}}," +
            "{\"wildcard\": {\"festival_area\": \"*?1*\"}}," +
            "{\"range\": {\"festival_end_date\": {\"gte\": \"now\"}}}" +
            "]}}")
    Page<MainPostDoc> findByFestivalGenreAndFestivalAreaContaining(String genre, String keyword, Pageable pageable);

    @Query("{\"bool\":{\"must\":[{\"match\":{\"festival_state\":\"공연중\"}}],\"filter\":[{\"range\":{\"festival_end_date\":{\"gte\":\"now\"}}}]}},\"sort\":[{\"festival_end_date\":{\"order\":\"asc\"}}]}")
    Page<MainPostDoc> findByFestivalStateAndFestivalEndDateGreaterThanOrderByFestivalEndDateAsc(String festivalState, Pageable pageable);

    @Query("""
        {
          "bool": {
            "must": [
              {
                "range": {
                  "festival_start_date": {
                    "gte": "now"
                  }
                }
              }
            ]
          }
        }
    """)
    Page<MainPostDoc> findUpcomingFestivals(Pageable pageable);
}
