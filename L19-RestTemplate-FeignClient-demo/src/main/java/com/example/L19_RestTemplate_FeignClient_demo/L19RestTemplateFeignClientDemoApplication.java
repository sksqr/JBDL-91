package com.example.L19_RestTemplate_FeignClient_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
public class L19RestTemplateFeignClientDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(L19RestTemplateFeignClientDemoApplication.class, args);
	}

	@Bean
	public RestTemplate getRestTemplate() {
		return new RestTemplate();
	}

}
