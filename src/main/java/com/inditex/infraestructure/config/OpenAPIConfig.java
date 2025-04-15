package com.inditex.infraestructure.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Inditex Price Service API")
                        .version("1.0")
                        .description("API for searching prices based on productId, brandId, and application date.")
                        .termsOfService("https://www.inditex.com/terms-and-conditions")
                );
    }


}
