package com.techartworks.helloworld.library.model;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
@Data
public class Oauth2Properties {
    private String jwkSetUri;
    private String issuerUri;
    private String jwsAlgorithm;
}
