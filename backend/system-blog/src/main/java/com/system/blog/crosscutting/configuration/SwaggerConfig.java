package com.system.blog.crosscutting.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI springShopOpenAPI() { // for general information in OpenAPI documentation 
	    return new OpenAPI()
	    	.info(new Info().title("Blog sAPI")
	        .description("This service exposes all endpoints necessary to obtain information from the database related to blog.")
	        .version("v0.0.3"))
	        .externalDocs(new ExternalDocumentation()
	        .description("springdoc-openapi")
	        .url("http://springdoc.org"));
	}
	

}
