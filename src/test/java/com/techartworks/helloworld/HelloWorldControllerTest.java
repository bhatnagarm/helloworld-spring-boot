package com.techartworks.helloworld;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class HelloWorldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello() throws Exception {
        mockMvc.perform(get("/hello")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("Hello world\n" +
                        "x-request-id: null\n" +
                        "x-b3-traceid: null\n" +
                        "x-b3-spanid: null\n" +
                        "x-b3-parentspanid: null\n" +
                        "x-b3-sampled: null\n" +
                        "x-b3-flags: null\n" +
                        "x-ot-span-context: null\n"));
    }

    @Test
    @Disabled("We have a set number of calls only to this endPoint.")
    void random_image() throws Exception {
        mockMvc.perform(get("/random_image")).andExpect(status().isOk());
    }

    @Test
    @Disabled("We have a set number of calls only to this endPoint.")
    void covid19info() throws Exception {
        this.mockMvc.perform(get("/covid")).andDo(print()).andExpect(status().isOk())
                .andExpect(jsonPath("$[:1].Country").value("South Africa"));
    }
}