package com.techartworks.helloworld.library.util;

import dasniko.testcontainers.keycloak.KeycloakContainer;
import io.restassured.RestAssured;
import jakarta.annotation.PostConstruct;
import org.apache.http.HttpHeaders;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URISyntaxException;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class KeycloakTestContainers {


    @LocalServerPort
    private int port;
    static final KeycloakContainer keycloak;

    static {
        keycloak = new KeycloakContainer().withRealmImportFile("keycloak/realm-export.json");
        keycloak.start();
    }

    @PostConstruct
    public void init() {
        RestAssured.baseURI = "http://localhost:" + port;
    }

    @DynamicPropertySource
    static void registerResourceServerIssuerProperty(DynamicPropertyRegistry registry) {
        registry.add("spring.security.oauth2.resourceserver.jwt.issuer-uri", () -> keycloak.getAuthServerUrl()
                + "/realms/TechArtWorks");
        registry.add("spring.security.oauth2.resourceserver.jwt.jwk-set-uri", () -> keycloak.getAuthServerUrl()
                + "/realms/TechArtWorks/protocol/openid-connect/certs");
    }

    protected String getClientCredBearer() {
            WebClient webClient = WebClient.builder()
                    .baseUrl(keycloak.getAuthServerUrl())
                    .defaultHeader("x-integration-test", "Test JWT")
                    .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                    .build();
            String result = webClient.post().uri("/realms/TechArtWorks/protocol/openid-connect/token")
                    .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                            .with("client_id", "test-cc").with("client_secret", "oZupthEMAs0BBwJQeKI4NEECbWdp3YIh"))
                    .retrieve()
                    .bodyToMono(String.class).block();

            return "Bearer " + (new JacksonJsonParser()).parseMap(result).get("access_token").toString();

    }

    /*protected String getAuthCodeBearer() {
        WebClient webClient = WebClient.builder()
                .baseUrl(keycloak.getAuthServerUrl())
                .defaultHeader("x-integration-test", "Test JWT")
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .build();
        String result = webClient.post().uri("/realms/TechArtWorks/protocol/openid-connect/token")
                .body(BodyInserters.fromFormData("grant_type", "client_credentials")
                        .with("client_id", "test-cc").with("client_secret", "oZupthEMAs0BBwJQeKI4NEECbWdp3YIh"))
                .retrieve()
                .bodyToMono(String.class).block();

        return "Bearer" + (new JacksonJsonParser()).parseMap(result).get("access_token").toString();

    }*/

}
