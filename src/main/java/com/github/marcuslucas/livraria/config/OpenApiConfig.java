package com.github.marcuslucas.livraria.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI().info(new Info()
                .title("API de Livrarias e Livros")
                .description("Microsserviço REST para gerenciamento de livrarias e livros (CP3 - Microservices and Web Engineering)")
                .version("v1")
                .contact(new Contact().name("Marcus Lucas")));
    }
}
