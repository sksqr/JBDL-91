package com.example.L13_minor_project_01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class McpServerDemoApp {

	public static void main(String[] args) {
		SpringApplication.run(McpServerDemoApp.class, args);
	}

}
