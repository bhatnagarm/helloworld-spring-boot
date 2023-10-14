package com.techartworks.helloworld.library;

import com.techartworks.helloworld.SecurityConfig;
import com.techartworks.helloworld.library.model.Oauth2Properties;
import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;

@EnableConfigurationProperties(value = Oauth2Properties.class)
@Import({SecurityConfig.class, EnableEncryptablePropertiesConfiguration.class})
@Profile("test")
public @interface TestSecurity {
}
