package com.aston.traston.starter.swagger.swagger;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = SwaggerProperties.PREFIX)
@Getter
@Setter
@Component
public class SwaggerProperties {

    public static final String PREFIX = "swagger.info.app";

    private String group;
    private String pathsToMatch;
    private boolean securityPath;
    private String title;
    private String description;
    private String version;

}
