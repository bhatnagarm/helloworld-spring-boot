package com.techartworks.helloworld.library.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Validated
@ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver.jwt")
public record Oauth2Properties (@NotBlank String jwkSetUri,
                                @NotBlank String issuerUri,
                                @NotEmpty List<String> jwsAlgorithms
                                ){
}
