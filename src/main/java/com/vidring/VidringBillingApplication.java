package com.vidring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { VidringBillingApplication.class, Jsr310JpaConverters.class })
@ComponentScan("com.vidring")
public class VidringBillingApplication {

	public static void main(String[] args) {
		SpringApplication.run(VidringBillingApplication.class, args);
	}

}
