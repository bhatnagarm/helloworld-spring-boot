package com.techartworks.helloworld.library;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.techartworks.helloworld.library.model.Oauth2Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.zalando.problem.spring.web.advice.security.SecurityProblemSupport;


import java.util.List;

@EnableWebSecurity
@Import(SecurityProblemSupport.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class OAuth2ClientSecurityConfig {

    @Autowired
    private Oauth2Properties oauth2Properties;

    @Autowired
    private SecurityProblemSupport securityProblemSupport;

    @Autowired
    private ObjectMapper objectMapper;

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> web.ignoring().antMatchers("/actuator","/actuator/*", "/private/api-docs");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        http
                .csrf().disable()   // csrf should be disabled for non-browser based URIs
                .authorizeHttpRequests((authz) -> authz
                        .antMatchers(securedPaths().toArray(new String[0]))
                        .authenticated()
                )
                .httpBasic().authenticationEntryPoint(securityProblemSupport)
                .and()
                .sessionManagement().enableSessionUrlRewriting(false)
                .sessionFixation().none()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(securityProblemSupport)
                .and()
                .exceptionHandling().accessDeniedHandler(securityProblemSupport)
                .and()
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
        return http.build();
    }

    @Bean
    JwtDecoder jwtDecoder() {
        return NimbusJwtDecoder
                .withJwkSetUri(oauth2Properties.getJwkSetUri())
                .jwsAlgorithm(SignatureAlgorithm.from(oauth2Properties.getJwsAlgorithm()))
                .build();
    }

    protected abstract List<String> securedPaths();
}
