package com.ll.hfback.domain.festival.search.repository;

import com.ll.hfback.domain.festival.search.document.MainPostDoc;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface PostDocRepository extends ElasticsearchRepository<MainPostDoc, String> {

    @Query("{\"match\": {\"festival_name\": {\"query\": \"?0\"}}}")
    Page<MainPostDoc> findByFestivalName(String keyword, Pageable pageable);
}
