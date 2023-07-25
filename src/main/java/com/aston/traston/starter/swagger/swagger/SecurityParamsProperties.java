package com.aston.traston.starter.swagger.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = SecurityParamsProperties.PREFIX)
@Getter
@Setter
@Component
public class SecurityParamsProperties {
    public static final String PREFIX = "swagger.security.app";
    private String schemeSecurity = "ApiKeyAuth";
    private String schemeName = "Authorization";
}
