package com.client.commandes.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Gestion Clients & Commandes")
                        .version("1.0")
                        .description("Développé par KING DIGITAL SERVICE")
                        .contact(new Contact()
                                .name("Service King digital")
                                .email("servicekingdigital1@gmail.com")
                                .url("https://ton-site-web.com")
                        )
                );
    }
}
