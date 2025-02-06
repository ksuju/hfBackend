package com.ll.hfback.domain.member.auth.config;

import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {

  @Value("${solapi.api-key}")
  private String apiKey;

  @Value("${solapi.api-secret}")
  private String apiSecret;

  @Bean
  public DefaultMessageService messageService() {
    return NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.solapi.com");
  }
}
