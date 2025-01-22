package com.ll.hfback.global.initData;

import com.ll.hfback.domain.festival.post.service.FestivalPostService;
import com.ll.hfback.domain.member.member.service.MemberService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.Transactional;

@Configuration
@Profile("!prod")
public class NotProd {
    @Bean
    public ApplicationRunner applicationRunner(
            FestivalPostService festivalPostService,
            MemberService memberService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {

            }
        };
    }
}
