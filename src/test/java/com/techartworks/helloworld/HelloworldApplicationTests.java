package com.techartworks.helloworld;


import com.ulisesbocchio.jasyptspringboot.configuration.EnableEncryptablePropertiesConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource("classpath:security-config-test.properties")
@Import(EnableEncryptablePropertiesConfiguration.class)
class HelloworldApplicationTests {

	@BeforeAll
	public static void before() {
		System.setProperty("jasypt.encryptor.password", "whatismypassword");
	}

	@Test
	void contextLoads() {
	}

}
