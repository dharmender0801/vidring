package com.vidring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableJpaRepositories
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
@SpringBootApplication
public class VidringBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidringBillingApplication.class, args);
	}

}
