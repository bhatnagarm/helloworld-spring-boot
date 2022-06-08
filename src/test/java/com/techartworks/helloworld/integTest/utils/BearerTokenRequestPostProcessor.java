package com.techartworks.helloworld.integTest.utils;

import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class BearerTokenRequestPostProcessor implements RequestPostProcessor {

    private final String token;

    public BearerTokenRequestPostProcessor(final String token) {
        this.token = token;
    }

    @Override
    public MockHttpServletRequest postProcessRequest(final MockHttpServletRequest request) {
        request.addHeader("Authorization", "Bearer " + this.token);
        return request;
    }

}
