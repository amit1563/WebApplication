package com.abcwebportal.webportal.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.abcwebportal.webportal.web.controller"))
				.paths(PathSelectors.any()) // paths(PathSelectors.ant("/foos/*"))
				.build();
	}

	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfo(" REST API for webportal", " All API realted to webportal offering", "API TOS",
				"Terms of service", new Contact("John Doe", "www.example.com", "myeaddress@company.com"),
				"License of API", "API license URL");
	}
}
