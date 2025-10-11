package com.bslogi.test;

import static com.bslogi.test.ApiSecurityConfig.DEFAULT_AUTH_TOKEN_HEADER_NAME;
import static com.bslogi.test.ApiSecurityConfig.DEFAULT_CSRF_HEADER_NAME;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

public class TestAuth {
    
    private static final Logger LOG = LoggerFactory.getLogger(TestAuth.class);

    private boolean isAlreadyLoggedIn;
    private TestRestTemplate testRestTemplate;
    private String username;
    private String password = "MetalRat316$";
    private String authToken;
    private String csrfToken;

    public TestAuth(TestRestTemplate testRestTemplate, String username) {
        this.username = username;
        this.testRestTemplate = testRestTemplate;
    }
    
    public void loginIfNotLoggedIn() {
        if (isAlreadyLoggedIn) {
            return;
        }
        String loginURL = "/api/login";
        ResponseEntity<String> get = testRestTemplate.exchange(loginURL, HttpMethod.GET, null, String.class, new Object[] {});
        authToken = get.getHeaders().getFirst(DEFAULT_AUTH_TOKEN_HEADER_NAME);
        csrfToken = get.getHeaders().getFirst(DEFAULT_CSRF_HEADER_NAME);
        
        HttpHeaders headers = createHeaders(authToken, csrfToken);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED); // else it'll take JSON by default
        MultiValueMap<String, String> postParams = new LinkedMultiValueMap<>();
        postParams.add("username", username);
        postParams.add("password", password);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(postParams, headers);
        ResponseEntity<String> result = testRestTemplate.exchange(loginURL, HttpMethod.POST, request, String.class);
        LOG.debug("login result: {}", result);
        authToken = result.getHeaders().getFirst(DEFAULT_AUTH_TOKEN_HEADER_NAME);
        csrfToken = result.getHeaders().getFirst(DEFAULT_CSRF_HEADER_NAME);
        isAlreadyLoggedIn = true;
    }
    
    public HttpHeaders initHeaders() {
        return createHeaders(authToken, csrfToken);
    }
    
    private HttpHeaders createHeaders(String authToken, String csrfToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        headers.add(DEFAULT_AUTH_TOKEN_HEADER_NAME, authToken);
        headers.add(DEFAULT_CSRF_HEADER_NAME, csrfToken);
        return headers;
    }

    public TestRestTemplate getTestRestTemplate() {
        return testRestTemplate;
    }
    public String getUsername() {
        return username;
    }
    public String getAuthToken() {
        return authToken;
    }
    public String getCsrfToken() {
        return csrfToken;
    }
    
    

}
