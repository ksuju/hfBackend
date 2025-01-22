package com.ll.hfback.domain.festival;


import com.ll.hfback.domain.festival.entity.KopisFesEntity;
import com.ll.hfback.domain.festival.service.KopisService;
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
public class FetchApisScheduler {

    @Value("${schedule.use}")
    private boolean useSchedule;

    //축제 OpenApi. 한국관광공사_국문 관광정보 서비스_GW 사용.
    @Value("${apis.api.service-key}")
    private String serviceKeyForApis;

    private final KopisService kopisService;

    @Scheduled(cron = "${schedule.cron_for_apis}")
    public void getApisApiData() {

        if(useSchedule == true) {

            log.info("apis스케쥴러 실행~");
            //stdate = eventStartDate
            List<KopisFesEntity> kopisFesEntity = new ArrayList<>();

            LocalDate now = LocalDate.now();
            YearMonth yearMonth = YearMonth.of(now.getYear(), now.getMonth());
            String stdate = yearMonth.atDay(1).format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            String eddate = yearMonth.atEndOfMonth().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

            String MobileOS = "ETC";   //OS구분
            String MobileApp = "LOCAL";//서비스명

            try{
                /*
                * 시작일과 종료일 값은, 그 기간내의 대상을 조회하는게 아니라,
                * 그 기간 사이에 개최되는 모든 행사를 조회한다.
                * 예를들어 20250101, 20250130으로 내가 조회한다고 했을때,
                * 축제중에 20240101 ~ 20250130 까지 1년간 개최되는 축제가 있다고 하면,
                * 그 대상은 조회된다.
                * */
                String apiUrl = String.format(
                        "https://apis.data.go.kr/B551011/KorService1/searchFestival1?MobileOS=%s&MobileApp=%s&eventStartDate=%s&eventEndDate=%s&serviceKey=%s&_type=json",
                        MobileOS, MobileApp, stdate, eddate, serviceKeyForApis
                );

                //RestTemplate방식은 에러가 발생하는 듯 하다.
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setRequestProperty("Content-Type", "application/json");
                connection.setRequestProperty("Accept", "application/json");

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
                StringBuilder responseBuilder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    responseBuilder.append(line);
                }
                reader.close();
                String jsonResponse = responseBuilder.toString();
                try {
                    kopisService.saveForApis(jsonResponse);
                }catch (Exception e){
                    log.error("Apis Scheduler save중에 에러가 발생했습니다", e);
                }
            } catch (Exception e) {
                log.error("Apis Scheduler Api 호출시 에러가 발생했습니다", e);
            }

            log.info("apis스케쥴러 종료~");
        }
    }
}
