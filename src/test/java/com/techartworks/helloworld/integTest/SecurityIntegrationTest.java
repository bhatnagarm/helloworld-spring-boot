package com.techartworks.helloworld.integTest;

import com.techartworks.helloworld.library.util.KeycloakTestContainers;
import io.restassured.RestAssured;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;

public class SecurityIntegrationTest extends KeycloakTestContainers {

    @Test
    void indexGreetsAuthenticatedUser() {
        RestAssured.given().header("Authorization", getClientCredBearer()).when().get("/hello")
                .then().body(CoreMatchers.containsString("Hello world"));
    }

}
