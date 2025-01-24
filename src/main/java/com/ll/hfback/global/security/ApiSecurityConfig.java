package com.ll.hfback.global.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class ApiSecurityConfig {

  private final JwtAuthorizationFilter jwtAuthorizationFilter;

  @Bean
  SecurityFilterChain apiFilterChain(HttpSecurity http) throws Exception {
    http
        .securityMatcher("/api/**")
        .authorizeHttpRequests(
            authorizeRequests -> authorizeRequests
                .requestMatchers(HttpMethod.GET, "/api/*/members").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/*/members/*").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/*/members/*/verify-password").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/*/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/*/auth/logout").permitAll()
                .anyRequest().permitAll()
        )
        .csrf(csrf -> csrf.disable())
        .httpBasic(httpBasic -> httpBasic.disable())
        .formLogin(formLogin -> formLogin.disable())
        .sessionManagement(
            sessionManagement -> sessionManagement.sessionCreationPolicy(
            SessionCreationPolicy.STATELESS)
        )
        .addFilterBefore(
            jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class
        );
    return http.build();
  }


}
