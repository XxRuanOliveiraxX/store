package com.lojavirtual.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API da Loja Virtual")
                        .description("API REST para gerenciamento de loja virtual")
                        .version("1.0")
                        .contact(new Contact()
                                .name("Equipe de Desenvolvimento")
                                .email("contato@lojavirtual.com")));
    }
} 