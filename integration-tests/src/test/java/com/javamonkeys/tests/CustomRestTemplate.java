package com.javamonkeys.tests;

import org.springframework.http.HttpHeaders;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Base64;

class CustomRestTemplate extends RestTemplate {

    private HttpHeaders headers = new HttpHeaders();

    public CustomRestTemplate() {
        super();
        setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
                return false;
            }

            @Override
            public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
                // do nothing
            }
        });
    }

    public void addHttpHeader(String name, String value){
        headers.set(name, value);
    }

    public void addBasicAuthHttpHeaders(String email, String password) {
        String src = email + ":" + password;
        headers.set("Authorization", "Basic " + Base64.getEncoder().encodeToString(src.getBytes()));
    }

    public void clearHttpHeaders() {
        headers.clear();
    }

    public HttpHeaders getHttpHeaders(){
        return headers;
    }
}