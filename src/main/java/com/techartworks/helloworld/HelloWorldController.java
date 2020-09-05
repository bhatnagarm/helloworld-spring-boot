package com.techartworks.helloworld;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import java.io.IOException;

@RestController
public class HelloWorldController {

    @NotBlank
    @Value("${secret.property}")
    private String property;

    @RequestMapping("/hello")
    public String hello() {
        return "Hello " + this.property;
    }

    @GetMapping("/random_image")
    public ResponseEntity<byte[]> random_image()  throws IOException {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url("https://source.unsplash.com/800x600/?artic-penguins") //TODO:artic-penguins
                .method("GET", null)
                .build();
        final byte[] image = client.newCall(request).execute().body().bytes();
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

}
