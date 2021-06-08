package com.near.platform.placesExtraction.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import static springfox.documentation.builders.PathSelectors.regex;

/**
 * http://localhost:8080/swagger-ui.html#/
 * http://localhost:8080/v2/api-docs
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
  @Bean
  public Docket productApi() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.near.platform.placesExtraction.controller"))
        .paths(regex("/v1.*"))
        .build();
  }
}