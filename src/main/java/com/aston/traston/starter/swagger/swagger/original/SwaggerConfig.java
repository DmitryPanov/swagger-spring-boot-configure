package com.aston.traston.starter.swagger.swagger.original;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
/**
 * Оригинальная настройка SwaggerConfig
 * ,но не во всех сервисах настроены настройки security
 */

@Configuration
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi publicApi(){
        return GroupedOpenApi.builder()
                .group("info-service")
                .pathsToMatch("/info-service/**")
                .build();
    }

    @Bean
    public OpenAPI customOpenApi(
            @Value("${spring.application.name}") String appName,
            @Value("${info.app.description}") String appDescription,
            @Value("${info.app.version}") String appVersion) {

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList("ApiKeyAuth"))
                .components(new Components().addSecuritySchemes("ApiKeyAuth",
                        new SecurityScheme()
                                .name("Authorization")
                                .in(SecurityScheme.In.HEADER)
                                .type(SecurityScheme.Type.APIKEY)))
                .info(new Info().title(appName)
                        .version(appVersion)
                        .description(appDescription));
    }
}
