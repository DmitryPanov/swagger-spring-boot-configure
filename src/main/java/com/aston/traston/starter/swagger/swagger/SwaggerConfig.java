package com.aston.traston.starter.swagger.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

@AutoConfiguration
@EnableConfigurationProperties({SwaggerProperties.class, SecurityParamsProperties.class})
@RequiredArgsConstructor
public class SwaggerConfig {

    @Autowired
    private final SwaggerProperties swaggerProperties;
    @Autowired
    private final SecurityParamsProperties securityParamsProperties;

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group(swaggerProperties.getGroup())
                .pathsToMatch(swaggerProperties.getPathsToMatch())
                .build();
    }

    @Bean
    @ConditionalOnProperty(value = "swagger.info.app.securityPath", havingValue = "true")
    public OpenAPI customOpenApiWithSecurity() {
        return new OpenAPI()
                .addSecurityItem(getSecurityItem())
                .components(getComponents())
                .info(createInfo());
    }

    @Bean
    @ConditionalOnProperty(value = "swagger.info.app.securityPath", havingValue = "false")
    public OpenAPI customOpenApi() {
        return new OpenAPI().info(createInfo());
    }

    @Bean
    public SecurityRequirement getSecurityItem() {
        return new SecurityRequirement()
                .addList(securityParamsProperties.getSchemeSecurity());
    }

    @Bean
    public Components getComponents() {
        return new Components().addSecuritySchemes(securityParamsProperties.getSchemeSecurity(),
                getAuthorization());
    }

    @Bean
    public SecurityScheme getAuthorization() {
        return new SecurityScheme()
                .name(securityParamsProperties.getSchemeName())
                .in(SecurityScheme.In.HEADER)
                .type(SecurityScheme.Type.APIKEY);
    }

    @Bean
    public Info createInfo() {
        return new Info().title(swaggerProperties.getTitle())
                .version(swaggerProperties.getVersion())
                .description(swaggerProperties.getDescription());
    }
}
