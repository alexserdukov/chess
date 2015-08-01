package com.javamonkeys.tests;

import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;

class CustomRestTemplate extends RestTemplate {

    private HttpHeaders headers = new HttpHeaders();

    public CustomRestTemplate() {
        super();
        setErrorHandler(new CustomResponseErrorHandler());
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