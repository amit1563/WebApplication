package com.abcwebportal.webportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * Entry point of the Application
 *
 */
@SpringBootApplication
//@EnableAutoConfiguration(exclude = {ErrorMvcAutoConfiguration.class})
public class WebportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebportalApplication.class, args);
	}
}
