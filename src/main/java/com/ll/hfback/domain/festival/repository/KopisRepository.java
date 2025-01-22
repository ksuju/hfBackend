package com.ll.hfback.domain.festival.repository;

import com.ll.hfback.domain.festival.entity.KopisFesEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KopisRepository extends JpaRepository<KopisFesEntity, Long> {
    //화면에서 입력한 Keyword 포함대상만 추출
    List<KopisFesEntity> findByFestivalNameContaining(String keyword);


    @Query("SELECT k FROM KopisFesEntity k WHERE :todayString BETWEEN k.festivalStartDate AND k.festivalEndDate ORDER BY FUNCTION('RAND')")
    List<KopisFesEntity> selectListForSlide(@Param("todayString") String todayString, Pageable pageable);
}
