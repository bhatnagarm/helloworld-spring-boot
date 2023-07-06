package com.techartworks.helloworld;

import com.techartworks.helloworld.library.OAuth2ClientSecurityConfig;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SecurityConfig extends OAuth2ClientSecurityConfig {


    @Override
    protected List<String> securedPaths() {
        return List.of("/hello", "/random_image", "/covid");
    }

}
