package com.techartworks.helloworld;

import com.techartworks.helloworld.domain.model.CountryInfo;
import com.techartworks.helloworld.library.TestSecurity;
import com.techartworks.helloworld.library.util.KeycloakTestContainers;
import io.restassured.RestAssured;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HelloWorldController.class)
@TestSecurity
public class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    RestTemplate restTemplate;
    @Test
    void indexGreetsAuthenticatedUser() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .with(jwt().jwt((jwt) -> jwt.claim("client_id","test-client"))))
                .andExpect(content().string(containsString("Hello world")));
    }

    @Test
    void testCovidInformation() throws Exception {
        CountryInfo[] countryInfo= {CountryInfo.builder().country("South Africa").build(), CountryInfo.builder().country("South Africa").build()};
        Mockito.when(restTemplate.getForObject(anyString(), eq(CountryInfo[].class))).thenReturn(countryInfo);
        this.mockMvc.perform(get("/covid")
                        .with(jwt()))
                .andExpect(content().string(containsString("South Africa")));
    }
}
