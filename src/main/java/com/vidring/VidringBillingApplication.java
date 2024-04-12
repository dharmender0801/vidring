package com.vidring;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EntityScan(basePackageClasses = { VidringBillingApplication.class, Jsr310JpaConverters.class })
@ComponentScan("com.vidring")
@EnableAsync
@PropertySource(ignoreResourceNotFound = true, value = {
		"file:${" + VidringBillingApplication.PROPERTIES_LOCATION_ENV + "}/"
				+ VidringBillingApplication.APPLICATION_PROPERTY + ".properties",
		"file:${" + VidringBillingApplication.PROPERTIES_LOCATION_ENV + "}/"
				+ VidringBillingApplication.ERROR_MESSAGES_PROPERTY + ".properties" })
@PropertySource(ignoreResourceNotFound = true, value = {
		"file:${" + VidringBillingApplication.PROPERTIES_LOCATION_ENV + "}/"
				+ VidringBillingApplication.APPLICATION_PROPERTY + ".properties",
		"file:${" + VidringBillingApplication.PROPERTIES_LOCATION_ENV + "}/"
				+ VidringBillingApplication.ERROR_MESSAGES_PROPERTY + ".properties" })
@EnableJpaRepositories
@EnableWebMvc
@EnableSpringDataWebSupport
@EnableCaching
@EnableSwagger2
@Slf4j
public class VidringBillingApplication {

	public static final String PROPERTIES_LOCATION_ENV = "spring.config.location";
	public static final String APPLICATION_PROPERTY = "vidring-billing-application";
	public static final String ERROR_MESSAGES_PROPERTY = "vidring-billing-error-messages";

	protected static final List<String> PROPERTY_FILES = Arrays.asList(APPLICATION_PROPERTY, ERROR_MESSAGES_PROPERTY);
	public static final String PROPERTIES_FILE_NAME = String.join(",", PROPERTY_FILES);

	public static void main(String[] args) {

		String configLocation = System.getProperty(VidringBillingApplication.PROPERTIES_LOCATION_ENV, "classpath:/");
		String configPath = configLocation + " - " + VidringBillingApplication.PROPERTIES_FILE_NAME;
		log.info("Configpath: {}", configPath);
		if (StringUtils.isNotBlank(configLocation)) {
			ConfigurableApplicationContext applicationContext = new SpringApplicationBuilder(
					VidringBillingApplication.class)
					.properties("spring.config.name:" + VidringBillingApplication.PROPERTIES_FILE_NAME,
							"spring.config.location:" + VidringBillingApplication.PROPERTIES_LOCATION_ENV,
							"spring.config.location:optional:classpath:/,optional:classpath:/config/propertminus-one-error-messagesies/")
					.build().run(args);
			applicationContext.getEnvironment();
		} else {
			SpringApplication.run(VidringBillingApplication.class, args);
		}
	}

}
