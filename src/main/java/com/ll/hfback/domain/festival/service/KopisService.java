package com.ll.hfback.domain.festival.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hfback.domain.festival.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.repository.KopisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KopisService {

    private final KopisRepository kopisRepository;


    public List<KopisFesEntity> selectListForSlide() {

        //무작위 5건을 조회해오기를 바랬지만,
        //쿼리결과를 캐싱할 수 있는 많은 경우의 수로 인해,
        //같은 쿼리로 조회한 결과값 자체를 무작위로 만들기는 어려워보인다.
        //캐싱설정을 없앨 방법이 있다고는 하는데.. 그걸 없애면서까지 하는게 의미가 있을지..
        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        String todayString = today.format(formatter);
        Pageable pageable = PageRequest.of(0, 5);

        return kopisRepository.selectListForSlide(todayString, pageable);
    }


    public List<KopisFesEntity> selectList(String keyword) {
       // return kopisRepository.findByFestivalNameContaining(keyword);
        return kopisRepository.findByFestivalNameContaining(keyword);
    }

    @Transactional
    public void saveForKopis(List<KopisFesEntity> kopisFesEntity) {
        kopisRepository.saveAll(kopisFesEntity);
    }

    @Transactional
    public void saveForApis(String jsonResponse) {

        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);

            JsonNode items = root.path("response").path("body").path("items").path("item");
            List<KopisFesEntity> festivalList = new ArrayList<>();

            for (JsonNode item : items) {
                KopisFesEntity entity = KopisFesEntity.builder()
                        .festivalId(item.path("contentid").asText())
                        .festivalName(item.path("title").asText())
                        .festivalStartDate(item.path("eventstartdate").asText())
                        .festivalEndDate(item.path("eventenddate").asText())
                        .festivalArea(item.path("addr1").asText())
                        .festivalHallName(item.path("addr1").asText())
                        .festivalState(null) // API에서 값이 없음
                        .festivalUrl(item.path("firstimage").asText())
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .inputType("APIS")
                        .build();

                festivalList.add(entity);
            }

            //saveAll시, id기준으로 기데이터의 update가 발생하지 않는 이유 확인필요.
            //어쩌면 id를 formatting하는 과정에서 생겼을 가능성 존재.
            kopisRepository.saveAll(festivalList);
            log.info(festivalList.toString());

        } catch (Exception e) {
            throw new RuntimeException("API 응답 처리 중 오류 발생", e);
        }
    }

}
