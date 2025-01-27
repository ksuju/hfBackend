package com.ll.hfback.global.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.config.EnableSpringDataWebSupport;

/**
 * packageName    : com.ll.hfback.global.web
 * fileName       : WebConfig
 * author         : sungjun
 * date           : 2025-01-27
 * description    : 자동 주석 생성
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025-01-27        kyd54       최초 생성
 */
@Configuration
@EnableSpringDataWebSupport(pageSerializationMode = EnableSpringDataWebSupport.PageSerializationMode.VIA_DTO)
public class WebConfig {
    // `@EnableSpringDataWebSupport`가 필요한 설정을 자동으로 활성화
}
