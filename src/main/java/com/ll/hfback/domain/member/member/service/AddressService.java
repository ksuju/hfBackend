package com.ll.hfback.domain.member.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ll.hfback.domain.member.member.dto.AddressResponse;
import com.ll.hfback.domain.member.member.dto.JusoApiResponse;
import com.ll.hfback.global.exceptions.ErrorCode;
import com.ll.hfback.global.exceptions.ServiceException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AddressService {

  private final StringRedisTemplate redisTemplate;
  private final WebClient webClient;
  private final ObjectMapper objectMapper;

  private static final String REDIS_KEY_PREFIX = "address:";



  @Value("${juso.api.url}")
  private String jusoApiUrl;

  @Value("${spring.profiles.active:dev}")
  private String activeProfile;

  @Value("${juso.api.key.dev}")
  private String devApiKey;

  @Value("${juso.api.key.prod}")
  private String prodApiKey;

  private String getApiKey() {
    return activeProfile.equals("prod") ? prodApiKey : devApiKey;
  }




  public List<AddressResponse> searchAddress(String keyword) {
    try {
      log.info("주소 검색 시작 - 키워드: {}", keyword);

      String cachedResult = redisTemplate.opsForValue().get(REDIS_KEY_PREFIX + keyword);
      if (cachedResult != null) {
        log.info("Redis 캐시에서 결과 찾음");
        return objectMapper.readValue(
            cachedResult, new TypeReference<List<AddressResponse>>() {}
        );
      }

      // 행정안전부 API 호출
      log.info("행정안전부 API 호출 시작");
      JusoApiResponse response = webClient.get()
          .uri(uriBuilder -> uriBuilder
              .scheme("https")
              .host("www.juso.go.kr")
              .path("/addrlink/addrLinkApi.do")
              .queryParam("confmKey", getApiKey())
              .queryParam("keyword", keyword)
              .queryParam("resultType", "json")
              .queryParam("countPerPage", "20")
              .build())
          .retrieve()
          .bodyToMono(String.class)  // 먼저 String으로 받아서 확인
          .doOnNext(rawJson -> log.info("API 원본 응답: {}", rawJson))
          .map(rawJson -> {
            try {
              return objectMapper.readValue(rawJson, JusoApiResponse.class);
            } catch (JsonProcessingException e) {
              throw new RuntimeException(e);
            }
          })
          .timeout(Duration.ofSeconds(5))
          .block();

      log.info("API 응답 변환 완료: {}", response);

      List<AddressResponse> addresses = _convertToAddressResponse(response);
      log.info("변환된 주소 목록: {}", addresses);

      // Redis 캐시 저장
      redisTemplate.opsForValue().set(
          REDIS_KEY_PREFIX + keyword,
          objectMapper.writeValueAsString(addresses),
          1,
          TimeUnit.HOURS
      );

      return addresses;

    } catch (Exception e) {
      log.error("주소 검색 중 오류 발생", e);
      throw new ServiceException(ErrorCode.API_CALL_FAILED);
    }
  }


  private List<AddressResponse> _convertToAddressResponse(JusoApiResponse response) {
    if (response == null || response.getResults() == null || response.getResults().getJuso() == null) {
      return List.of();
    }

    return response.getResults().getJuso().stream()
        .map(juso -> AddressResponse.builder()
            .roadAddr(juso.getRoadAddr())
            .jibunAddr(juso.getJibunAddr())
            .zipNo(juso.getZipNo())
            .build())
        .collect(Collectors.toList());
  }

}
