package com.example.L21_email_sender_circuit_breaker_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class L21EmailSenderCircuitBreakerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(L21EmailSenderCircuitBreakerDemoApplication.class, args);
	}

}
