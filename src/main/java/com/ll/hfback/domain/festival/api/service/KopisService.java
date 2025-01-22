package com.ll.hfback.domain.festival.api.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hfback.domain.festival.api.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.api.repository.KopisRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class KopisService {

    private final KopisRepository kopisRepository;

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

                //Apis와 Kopis의 id값이 우연히라도 겹치면 문제가 되는고로,
                //Apis의 경우 접두어 A-를 붙혀서 Id를 관리 >> 어차피 kopis에선 PF라는 키워드를 접두어로 ID생성
                //String contentId = item.path("contentid").asText();
                //String formattedFestivalId = String.format("A-%s", contentId);

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
