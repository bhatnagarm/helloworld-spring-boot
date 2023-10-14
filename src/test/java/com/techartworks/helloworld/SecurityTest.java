package com.techartworks.helloworld;

import com.techartworks.helloworld.domain.model.Author;
import com.techartworks.helloworld.library.TestSecurity;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.List;

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

    @BeforeAll
    public static void before() {
        System.setProperty("jasypt.encryptor.password", "whatismypassword");
    }

    @Test
    void indexGreetsAuthenticatedUser() throws Exception {
        this.mockMvc.perform(get("/hello")
                        .with(jwt().jwt((jwt) -> jwt.claim("client_id","test-client"))))
                .andExpect(content().string(containsString("Hello world")));
    }

    @Test
    void testCovidInformation() throws Exception {
        Author author = new Author(
                "Sachi Routray",
                "Sachi Rautroy",
                "2004",
                List.of(
                        "Satchidananda Raut Roy",
                        "Satchidananda Raut-Roy",
                        "Satchidananda Rautroy",
                        "Satchi Raut Roy",
                        "Satchi Raut-Roy",
                        "Satchi Rautroy",
                        "Saccidānanda Rāutarāẏa",
                        "Yugashrashta Sachi Routray",
                        "Sacci Rāutarāẏa",
                        "Sachidananda Routray",
                        "Rāutarāẏa, Saccidānanda",
                        "ସଚ୍ଚିଦାନନ୍ଦ ରାଉତରାୟ ଓଡ଼ିଆ ଲେଖକ",
                        "Sachi Rautroy"
                ),
                "/authors/OL1A",
                "1916",
                new Author.AuthorType("/type/author"),
                new Author.RemoteIds("Q5320436", "0000000119387514", "107786978"),
                List.of(11538051),
                new Author.Bio("/type/text", "Sachidananda Routray (13 May 1916 – 21 August 2004) was an Indian poet, novelist and short-story writer who wrote in Odia. He received Jnanpith Award, the highest literary award of India, in 1986. He was popularly known as Sachi Routray."),
                13,
                13,
                new Author.Created("/type/datetime", "2008-04-01T03:28:50.625462"),
                new Author.LastModified("/type/datetime", "2021-08-01T00:38:55.954708")
        );
        Mockito.when(restTemplate.getForObject(anyString(), eq(Author.class))).thenReturn(author);
        this.mockMvc.perform(get("/author")
                        .with(jwt()))
                .andExpect(content().string(containsString("Sachi Routray")));
    }
}
