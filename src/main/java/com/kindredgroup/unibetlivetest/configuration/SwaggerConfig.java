package com.kindredgroup.unibetlivetest.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    
    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("UnibestLiveTest API"))
                .externalDocs(new ExternalDocumentation()
                .description("UnibetLiveTest Documentation")
                .url("https://github.com/EtiennePlas1/UnibetLiveTest"));
    }
}
