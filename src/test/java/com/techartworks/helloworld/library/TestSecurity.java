package com.techartworks.helloworld.library;

import com.techartworks.helloworld.SecurityConfig;
import com.techartworks.helloworld.library.model.Oauth2Properties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;

@Import(SecurityConfig.class)
@EnableConfigurationProperties(value = Oauth2Properties.class)
public @interface TestSecurity {
}
