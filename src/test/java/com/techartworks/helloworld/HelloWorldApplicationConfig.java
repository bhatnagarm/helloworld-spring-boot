package com.techartworks.helloworld;

import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.devtools.restart.RestartScope;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration(proxyBeanMethods = false)
@TestPropertySource("classpath:security-config-test.properties")
@Import(EnableEncryptablePropertiesConfiguration.class)
public class HelloWorldApplicationConfig {

    @Bean
    @ServiceConnection
    @RestartScope
    PostgreSQLContainer<?> postgresContainer() {
        return new PostgreSQLContainer<>(DockerImageName.parse("postgres:latest"));
    }

/*	@Bean
	@RestartScope
	@ServiceConnection(name = "openzipkin/zipkin")
	GenericContainer<?> zipkinContainer() {
		return new GenericContainer<>(DockerImageName.parse("openzipkin/zipkin:latest"))
				.withExposedPorts(9411)
				.withAccessToHost(true);

	}*/

    public static void main(String[] args) {
        SpringApplication.from(HelloworldApplication::main)
                .with(HelloWorldApplicationConfig.class).run(args);
    }
}
