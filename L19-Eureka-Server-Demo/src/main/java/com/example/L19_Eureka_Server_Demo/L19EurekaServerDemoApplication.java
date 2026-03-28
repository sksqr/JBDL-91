package com.example.L19_Eureka_Server_Demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class L19EurekaServerDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(L19EurekaServerDemoApplication.class, args);
	}

}
