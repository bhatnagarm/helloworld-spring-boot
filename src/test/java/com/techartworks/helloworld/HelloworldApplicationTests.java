package com.techartworks.helloworld;


import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:security-config-test.properties")
class HelloworldApplicationTests {

	@Test
	void contextLoads() {
	}

}
