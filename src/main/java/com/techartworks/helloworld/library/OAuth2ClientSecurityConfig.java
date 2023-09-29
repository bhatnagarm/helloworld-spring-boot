package com.techartworks.helloworld.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techartworks.helloworld.library.model.Oauth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;

import java.util.List;

@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@EnableMethodSecurity
public abstract class OAuth2ClientSecurityConfig {

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    private SecurityProblemSupport securityProblemSupport;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().requestMatchers("/actuator","/actuator/*", "/private/api-docs");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)   // csrf should be disabled for non-browser based URIs
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(securedPaths().toArray(new String[0]))
                        .authenticated()
                )
                .httpBasic(httpBasic -> httpBasic.authenticationEntryPoint(securityProblemSupport))
                .sessionManagement(session -> session.enableSessionUrlRewriting(false)
                        .sessionFixation().none()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exceptionHandler -> exceptionHandler
                        .authenticationEntryPoint(securityProblemSupport)
                        .accessDeniedHandler(securityProblemSupport))
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder()))
                        );
        return http.build();
    }

    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(oauth2Properties.jwkSetUri())
                .jwsAlgorithms(signatureAlgorithms -> {
                    oauth2Properties.jwsAlgorithms()
                            .forEach(algorithm -> signatureAlgorithms.add(SignatureAlgorithm.from(algorithm)));
                })
                .build();
    }

    protected abstract List<String> securedPaths();
}
