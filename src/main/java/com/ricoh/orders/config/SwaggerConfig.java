package com.ricoh.orders.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

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
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.any()).build().apiInfo(this.apiInfo());
	}

	private ApiInfo apiInfo() {
		//TODO fake information to complete de API info
		return new ApiInfo(
				"REST API",
				"Springboot + Mysql + REST API application based on Orders, Articles and Catalogues.",
				"0.0.1-SNAPSHOT",
				"All rights otorged",
				new Contact("Carlos Ordonez Gonzalez", "https://google.es", "carlos.ord.gon@gmail.com"),
				"API license",
				"http://www.google.es",
				Collections.emptyList());
	}

}
