package com.ll.hfback.global.initData;

import com.ll.hfback.domain.festival.post.service.FestivalPostService;
import com.ll.hfback.domain.member.auth.dto.SignupRequest;
import com.ll.hfback.domain.member.auth.service.AuthService;
import com.ll.hfback.domain.member.member.entity.Member;
import com.ll.hfback.domain.member.member.entity.Member.Gender;
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
            AuthService authService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member1 = authService.signup(
                    new SignupRequest(
                        "test1@testtest.com", "1234", "강",
                        Gender.M, true, "010-1244-5151"
                    )
                );
                Member member2 = authService.signup(
                    new SignupRequest(
                        "test2@testtest.com", "1234", "남",
                        Gender.M, false, "010-1244-5151"
                    )
                );
                Member member3 = authService.signup(
                    new SignupRequest(
                        "test3@testtest.com", "1234", "역",
                        Gender.W, true, "010-1244-5151"
                    )
                );
            }
        };
    }
}
