package com.ll.hfback.domain.festival.api.serviceImpl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hfback.domain.festival.api.service.ApiService;
import com.ll.hfback.domain.festival.post.entity.Post;
import com.ll.hfback.domain.festival.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApiServiceImpl implements ApiService {
    private final PostRepository postRepository;

    // Apis의 데이터를 Post에 저장
    @Override
    @Transactional
    public void saveForApis(String jsonResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode root = objectMapper.readTree(jsonResponse);
            JsonNode items = root.path("response").path("body").path("items").path("item");

            List<Post> festivalList = new ArrayList<>();
            LocalDate today = LocalDate.now(); // 오늘 날짜 가져오기
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");

            for (JsonNode item : items) {
                String festivalStartDateRaw = item.path("eventstartdate").asText();
                String festivalEndDateRaw = item.path("eventenddate").asText();

                // 날짜 변환
                LocalDate startDate = LocalDate.parse(festivalStartDateRaw, inputFormatter);
                LocalDate endDate = LocalDate.parse(festivalEndDateRaw, inputFormatter);

                // 오늘 날짜를 기준으로 지난 데이터는 건너뜀
                if (endDate.isBefore(today)) {
                    continue;
                }

                // 변환된 날짜를 "yyyy.MM.dd" 형식으로 저장
                String festivalStartDate = startDate.format(outputFormatter);
                String festivalEndDate = endDate.format(outputFormatter);

                Post entity = Post.builder()
                        .festivalId(item.path("contentid").asText())
                        .festivalName(item.path("title").asText())
                        .festivalStartDate(festivalStartDate)
                        .festivalEndDate(festivalEndDate)
                        .festivalArea(item.path("addr1").asText())
                        .festivalHallName(item.path("addr1").asText())
                        .festivalState(null)
                        .festivalUrl(item.path("firstimage").asText())
                        .createDate(LocalDateTime.now())
                        .modifyDate(LocalDateTime.now())
                        .genrenm("축제")
                        .inputType("APIS")
                        .build();
                festivalList.add(entity);
            }
            postRepository.saveAll(festivalList);
            log.info(festivalList.toString());
        } catch (Exception e) {
            throw new RuntimeException("API 응답 처리 중 오류 발생", e);
        }
    }

    // Kopis의 데이터를 Post에 저장
    @Override
    @Transactional
    public void saveForKopis(List<Post> posts) {
        postRepository.saveAll(posts);
    }

    // KOPIS에서 받아온 xml데이터를 Post객체로 변환
    @Override
    @Transactional
    public List<Post> parseXmlToEntity(String xml) throws Exception {
        List<Post> kopisEntity = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

        // 오늘 날짜를 LocalDate로 가져옴
        LocalDate today = LocalDate.now();

        NodeList dbElements = document.getElementsByTagName("db");
        for (int i = 0; i < dbElements.getLength(); i++) {
            Element element = (Element) dbElements.item(i);

            String festivalId = getTagValue("mt20id", element);
            String festivalName = getTagValue("prfnm", element);
            String festivalStartDate = getTagValue("prfpdfrom", element);
            String festivalEndDate = getTagValue("prfpdto", element);
            String festivalHallName = getTagValue("fcltynm", element);
            String festivalUrl = getTagValue("poster", element);
            String festivalArea = getTagValue("area", element);
            String festivalState = getTagValue("prfstate", element);
            String genrenm = getTagValue("genrenm", element);

            // festivalEndDate를 LocalDate로 변환
            LocalDate endDate = LocalDate.parse(festivalEndDate, DateTimeFormatter.ofPattern("yyyy.MM.dd"));

            // 오늘 날짜를 기준으로 지난 데이터는 건너뜀
            if (endDate.isBefore(today)) {
                continue;
            }

            Post post = Post.builder()
                    .festivalId(festivalId)
                    .festivalName(festivalName)
                    .festivalStartDate(festivalStartDate)
                    .festivalEndDate(festivalEndDate)
                    .festivalHallName(festivalHallName)
                    .festivalUrl(festivalUrl)
                    .festivalArea(festivalArea)
                    .festivalState(festivalState)
                    .createDate(LocalDateTime.now())
                    .modifyDate(LocalDateTime.now())
                    .genrenm(genrenm)
                    .inputType("KOPIS")
                    .build();

            kopisEntity.add(post);
        }
        return kopisEntity;
    }

    // xml의 <db>태그와 매칭되는 데이터 추출 메서드
    @Override
    @Transactional
    public String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "없음";
    }
}
