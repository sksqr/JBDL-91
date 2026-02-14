package com.example.L09_Logging_MVC_Annotation_demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class L09LoggingMvcAnnotationDemoApplication {

	private static Logger LOGGER = LoggerFactory.getLogger(L09LoggingMvcAnnotationDemoApplication.class);

	public static void main(String[] args) {

		LOGGER.debug("Debug log..");
		LOGGER.info("Info log..");
		LOGGER.warn("Warn log..");
		LOGGER.error("Error log..");
		SpringApplication.run(L09LoggingMvcAnnotationDemoApplication.class, args);
		LOGGER.debug("Debug log..");
		LOGGER.info("Info log..");
		LOGGER.warn("Warn log..");
		LOGGER.error("Error log..");



	}

}
