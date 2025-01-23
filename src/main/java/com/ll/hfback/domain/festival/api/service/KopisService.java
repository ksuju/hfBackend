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
                        .genrenm(null)//Apis는 장르명을 전달해주지 않음.
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .inputType("APIS")
                        .build();

                festivalList.add(entity);
            }


            kopisRepository.saveAll(festivalList);
            log.info(festivalList.toString());

        } catch (Exception e) {
            throw new RuntimeException("API 응답 처리 중 오류 발생", e);
        }
    }

}
