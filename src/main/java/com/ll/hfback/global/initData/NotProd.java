package com.ll.hfback.global.initData;

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
            AuthService authService
    ) {
        return new ApplicationRunner() {
            @Transactional
            @Override
            public void run(ApplicationArguments args) throws Exception {
                Member member1 = authService.signup(
                    new SignupRequest(
                        "test1@test.com", "1234", "강남",
                        Gender.M, true, "010-1111-1111"
                    )
                );
                Member member2 = authService.signup(
                    new SignupRequest(
                        "test2@test.com", "1234", "남영동",
                        Gender.M, false, "010-2222-2222"
                    )
                );
                Member member3 = authService.signup(
                    new SignupRequest(
                        "test3@test.com", "1234", "역삼",
                        Gender.W, true, "010-3333-3333"
                    )
                );
            }
        };
    }
}
