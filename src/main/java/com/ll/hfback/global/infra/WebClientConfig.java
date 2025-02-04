package com.ll.hfback.global.infra;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
public class WebClientConfig {

  @Bean
  public WebClient webClient() {
    return WebClient.builder()
        // 기본 헤더 설정
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)

        // 요청/응답 로깅을 위한 필터 추가
        .filter(logRequest())
        .filter(logResponse())

        // 응답 데이터 크기 제한 설정
        .codecs(configurer ->
            configurer.defaultCodecs().maxInMemorySize(2 * 1024 * 1024)) // 2MB

        .build();
  }


  private ExchangeFilterFunction logRequest() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      log.info("Request: {} {}", clientRequest.method(), clientRequest.url());
      return Mono.just(clientRequest);
    });
  }


  private ExchangeFilterFunction logResponse() {
    return ExchangeFilterFunction.ofResponseProcessor(clientResponse -> {
      log.info("Response status: {}", clientResponse.statusCode());
      return Mono.just(clientResponse);
    });
  }

}
