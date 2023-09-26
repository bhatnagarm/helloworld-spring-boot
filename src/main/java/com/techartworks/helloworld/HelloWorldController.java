package com.techartworks.helloworld;

import com.techartworks.helloworld.domain.model.CountryInfo;
import com.techartworks.helloworld.library.model.LoginForm;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.Map;

@RestController
public class HelloWorldController {

    private final static Logger log = LoggerFactory.getLogger(HelloWorldController.class);

    @NotBlank
    @Value("${secret.property}")
    private String property;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/hello")
    //@PreAuthorize("hasAnyAuthority('SCOPE_user.read')")
    public String hello(@RequestHeader Map<String, String> headers,
                        @AuthenticationPrincipal Jwt jwt) {
        return "Hello " + this.property + "\n" +
                "principal_subject: " + jwt.getSubject() + "\n" +
                "preferred_username: " + jwt.getClaimAsString("preferred_username") + "\n" +
                "x-request-id: " + headers.get("x-request-id") + "\n" +
                "x-b3-traceid: " + headers.get("x-b3-traceid") + "\n" +
                "x-b3-spanid: " + headers.get("x-b3-spanid") + "\n" +
                "x-b3-parentspanid: " + headers.get("x-b3-parentspanid") + "\n" +
                "x-b3-sampled: " + headers.get("x-b3-sampled") + "\n" +
                "x-b3-flags: " + headers.get("x-b3-flags") + "\n" +
                "x-ot-span-context: " + headers.get("x-ot-span-context") + "\n";
    }

/*    @GetMapping("/createmembermock")
    public String createMemberMock(@RequestBody final LoginForm loginForm, @RequestHeader Map<String, String> headers) {
        return "Hello " + this.property + "\n" +
                "LoginForm.FirstName: " + loginForm.getFirstName() + "\n" +
                "LoginForm.LastName: " + loginForm.getLastName() + "\n" +
                "LoginForm.Email: " + loginForm.getEmail() + "\n" +
                "LoginForm.Mobile: " + loginForm.getMobile() + "\n" +
                "x-request-id: " + headers.get("x-request-id") + "\n" +
                "x-b3-traceid: " + headers.get("x-b3-traceid") + "\n" +
                "x-b3-spanid: " + headers.get("x-b3-spanid") + "\n" +
                "x-b3-parentspanid: " + headers.get("x-b3-parentspanid") + "\n" +
                "x-b3-sampled: " + headers.get("x-b3-sampled") + "\n" +
                "x-b3-flags: " + headers.get("x-b3-flags") + "\n" +
                "x-ot-span-context: " + headers.get("x-ot-span-context") + "\n";
    }*/

    @GetMapping("/random_image")
    public ResponseEntity<byte[]> random_image(@RequestHeader final HttpHeaders headers)  throws IOException {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url("https://source.unsplash.com/800x600/?artic-penguins") //TODO:artic-penguins
                .method("GET", null)
                .build();
        final byte[] image = client.newCall(request).execute().body().bytes();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @GetMapping("/covid")
    public CountryInfo[] covid19info() {
        log.warn("Call to HelloWorld Rest API");
        return restTemplate.getForObject("https://api.covid19api.com/live/country/south-africa/status/confirmed", CountryInfo[].class);
    }

}
