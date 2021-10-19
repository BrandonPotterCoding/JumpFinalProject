package com.cognixia.jump.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import springfox.documentation.builders.ApiInfoBuilder;
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
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiDetails())
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class) )
				.paths(PathSelectors.any())
				.build();
	}
	
	private ApiInfo apiDetails() {
		return new ApiInfo(
				"Todos Project API", 
				"Pley Jump Final Project", 
				"2.0", 
				"Free", 
				new Contact("Brandon", "N/A", "email@email.com"), 
				"API Lisecense", 
				"N/A",
				Collections.emptyList());
	}
	
	
	// Not using this method, but it is a valid alternative to the above
	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("JavaInUse API")
				.description("JavaInUse API reference for developers")
				.termsOfServiceUrl("http://javainuse.com")
				.contact(new Contact("Brandon", "N/A", "email@email.com"))
				.license("JavaInUse License")
				.licenseUrl("javainuse@gmail.com")
				.version("1.0")
				.build();
	}

}
