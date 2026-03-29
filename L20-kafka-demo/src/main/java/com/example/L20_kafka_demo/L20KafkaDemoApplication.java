package com.example.L20_kafka_demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;

@SpringBootApplication
@EnableKafka
public class L20KafkaDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(L20KafkaDemoApplication.class, args);
	}

}
