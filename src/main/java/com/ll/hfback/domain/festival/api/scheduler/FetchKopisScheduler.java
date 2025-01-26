package com.ll.hfback.domain.festival.api.scheduler;


import com.ll.hfback.domain.festival.api.service.ApiService;
import com.ll.hfback.domain.festival.post.entity.Post;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

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
@EnableScheduling
@Configuration
public class FetchKopisScheduler {

    @Value("${schedule.use}")
    private boolean useSchedule;

    //공연 OpenApi. KOPIS Open Api 사용.
    @Value("${kopis.api.service-key}")
    private String serviceKeyForKopis;

    private final ApiService apiService;

    @Scheduled(cron = "${schedule.cron_for_kopis}")
    public void getKopisApiData() {

        if(useSchedule == true) {
            try {
                log.info("Kopis스케쥴러 실행");

                /*조회 후 Entity에 Insert */
                List<Post> post = new ArrayList<>();

                /*각 달의 첫날과 마지막날 추출*/
                LocalDate now = LocalDate.now();
                YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
                String stdate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
                String eddate = yearMonth.atEndOfMonth().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

                /*가져올 데이터 row와 page. page는 필수값인데,
                 * 총 추출 데이터를 알 방법이 없다.   단, 다른 축제 페이지 기준 한 달 30건 가량이 잡히는고로,
                 * 100row * 5page해서 대략 500건 가량이 있다고 가정하고 추출 작업 진행
                 * */
                int rows = 100;
                int currentPage = 1;
                int maxPage = 5;

                while (currentPage <= maxPage) {

                    String apiUrl = String.format(
                            "http://www.kopis.or.kr/openApi/restful/pblprfr?service=%s&stdate=%s&eddate=%s&rows=%d&cpage=%d",
                            serviceKeyForKopis, stdate, eddate, rows, currentPage
                    );


                    URL url = new URL(apiUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setRequestProperty("Content-Type", "application/xml");


                    BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                    StringBuilder responseBuilder = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        responseBuilder.append(line);
                    }
                    reader.close();

                    String xmlResponse = responseBuilder.toString();

                    // XML 응답에서 데이터를 파싱하고 리스트에 추가
                    List<Post> parsedData = apiService.parseXmlToEntity(xmlResponse);
                    post.addAll(parsedData);

                    currentPage++;
                }
                try {
                    apiService.saveForKopis(post);
                } catch (Exception e) {
                    log.error("Kopis Scheduler save중에 에러가 발생했습니다", e);
                }
            } catch (Exception e) {
                log.error("Kopis Scheduler Api 호출시 에러가 발생했습니다", e);
            }
        }
        log.info("Kopis스케쥴러 종료");
    }
}