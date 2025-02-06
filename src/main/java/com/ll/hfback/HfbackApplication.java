package com.ll.hfback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@EnableJpaAuditing
public class HfbackApplication {

	public static void main(String[] args) {
		System.getProperties().setProperty("aws.java.v1.disableDeprecationAnnouncement", "true");
		SpringApplication.run(HfbackApplication.class, args);
	}
}
