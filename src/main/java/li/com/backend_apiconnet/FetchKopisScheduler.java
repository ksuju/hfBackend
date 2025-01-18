package li.com.backend_apiconnet;


import li.com.backend_apiconnet.entity.KopisFesEntity;
import li.com.backend_apiconnet.service.KopisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class FetchKopisScheduler {

    @Value("${schedule.use}")
    private boolean useSchedule;

    @Value("${kopis.api.service-key}")
    private String serviceKey;

    private final KopisService kopisService;

    @Scheduled(cron = "${schedule.cron}")
    public void getKopisApiData() {

        if(useSchedule == true) {

            try {

                log.info("getKopisApiData 실행");

                /*조회 후 Entity에 Insert*/
                List<KopisFesEntity> kopisFesEntity = new ArrayList<>();


                /*각 달의 첫날과 마지막날 추출*/
                LocalDate now = LocalDate.now();
                YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
                String stdate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                String eddate = yearMonth.atEndOfMonth().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                /*가져올 데이터 row와 page. page는 필수값인데,
                 * 총 추출 데이터를 알 방법이 없다.   단, 다른 축제 페이지 기준 한 달 30건 가량이 잡히는고로,
                 * 100row * 3page해서 대략 300건 가량이 있다고 가정하고 추출 작업 진행
                 * */
                int rows = 100;
                int currentPage = 1;
                int maxPage = 5;

                while (currentPage <= maxPage) {

                    String apiUrl = String.format(
                            "http://www.kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&rows=%d&cpage=%d",
                            serviceKey, stdate, eddate, rows, currentPage
                    );

                    // HTTP 연결
                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/xml");

                    // 응답 읽기
                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();

                    String xmlResponse = responseBuilder.toString();

                    // XML 응답에서 데이터를 파싱하고 리스트에 추가
                    List<KopisFesEntity> parsedData = parseXmlToEntity(xmlResponse);
                    kopisFesEntity.addAll(parsedData);

                    currentPage++;
                }

                try {
                    kopisService.save(kopisFesEntity);
                } catch (Exception e) {
                    log.error("Kopis Scheduler save중에 에러가 발생했습니다", e);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{

        }
    }

    private List<KopisFesEntity> parseXmlToEntity(String xml) throws Exception {
        List<KopisFesEntity> kopiseEntity = new ArrayList<>();

        // XML 파싱을 위한 DocumentBuilder 생성
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new java.io.ByteArrayInputStream(xml.getBytes()));

        // <db> 태그 가져오기
        NodeList dbElements = document.getElementsByTagName("db");

        // 각 <db> 태그의 데이터 추출
        for (int i = 0; i < dbElements.getLength(); i++) {
            Element element = (Element) dbElements.item(i);

            /*
             * 공연Id(mt20id)        - festivalId
            * 공연명(prfnm)          - festivalName
            * 공연 시작일(prfpdfrom) - festivalStartDate
            * 공연 종료일(prfpdto)   - festivalEndDate
            * 공연 지역(area)        - festivalArea
            * 공연 시설 명(fcltynm)  - festivalHallName
            * 공연 상태(prfstate)     -festivalState
            * 공연 URL ( 아마.. 포스터인가?,  poster)-festivalUrl
            * */
            String festivalId = getTagValue("mt20id", element);
            String festivalName = getTagValue("prfnm", element);
            String festivalStartDate = getTagValue("prfpdfrom", element);
            String festivalEndDate = getTagValue("prfpdto", element);
            String festivalHallName = getTagValue("fcltynm", element);
            String festivalUrl = getTagValue("poster", element);
            String festivalArea = getTagValue("area", element);
            String festivalState = getTagValue("prfstate", element);

            /*String genrenm = getTagValue("genrenm", element);
            String openrun = getTagValue("openrun", element);*/

            KopisFesEntity kopisFesEntity = KopisFesEntity.builder()
                    .festivalId(festivalId)
                    .festivalName(festivalName)
                    .festivalStartDate(festivalStartDate)
                    .festivalEndDate(festivalEndDate)
                    .festivalHallName(festivalHallName)
                    .festivalUrl(festivalUrl)
                    .festivalArea(festivalArea)
                    .festivalState(festivalState)
                    .build();

            kopiseEntity.add(kopisFesEntity);
        }

        return kopiseEntity;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent();
        }
        return "없음";
    }
}