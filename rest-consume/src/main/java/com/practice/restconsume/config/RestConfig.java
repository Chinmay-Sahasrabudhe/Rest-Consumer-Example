package com.practice.restconsume.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {
	
	@Bean
	public RestTemplate getRestTemplate(RestTemplateBuilder restTemplateBuilder) {
		
		return restTemplateBuilder.setConnectTimeout(Duration.ofMillis(3000))
				.setReadTimeout(Duration.ofMillis(3000)).build();
	}

}
