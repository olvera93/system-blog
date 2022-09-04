package com.system.blog.crosscutting.configuration;

import java.util.Arrays;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public OpenAPI springShopOpenAPI() { // for general information in OpenAPI documentation 
	    return new OpenAPI()
	    	.addServersItem(new Server().url("/"))
	    	.components(new Components().addSecuritySchemes("bearerAuth",
		                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
		                        .in(SecurityScheme.In.HEADER).name("Authorization")))
	    	.info(new Info().title("Blog sAPI")
	        .description("This service exposes all endpoints necessary to obtain information from the database related to blog.")
	        .version("v0.0.4"))
	        .externalDocs(new ExternalDocumentation()
	        .description("springdoc-openapi")
	        .url("http://springdoc.org"))
	        .addSecurityItem(new SecurityRequirement().addList("bearerAuth", Arrays.asList("read", "write")));

	        
	}
	

}
