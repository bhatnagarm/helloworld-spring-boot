package com.techartworks.helloworld;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HelloWorldController.class)
@Import(SecurityConfig.class)
public class SecurityTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    void indexGreetsAuthenticatedUser() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .with(jwt().jwt((jwt) -> jwt.claim("client_id","test-client"))))
                .andExpect(content().string(is("Hello world")));
    }
}
