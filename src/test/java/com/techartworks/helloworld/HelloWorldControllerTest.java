package com.techartworks.helloworld;

import com.techartworks.helloworld.library.TestSecurity;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.CoreMatchers.containsString;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(HelloWorldController.class)
@TestSecurity
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void hello() throws Exception {
        this.mvc.perform(get("/hello")
                        .with(jwt().jwt((jwt) -> jwt.claim("client_id","test-client"))))
                .andExpect(content().string(containsString("Hello world")));
    }

    @Test
    void createMemberMock() {
    }

    @Test
    void random_image() {
    }

    @Test
    void covid19info() {
    }
}