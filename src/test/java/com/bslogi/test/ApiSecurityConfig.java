package com.bslogi.test;

import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;

public class ApiSecurityConfig  {

    public static final String API_ROOT_PATH = "/api";
    public static final String LOGIN_URL = API_ROOT_PATH + "/login";
    public static final String LOGOUT_POST_URL = API_ROOT_PATH + "/logout";
    public static final String CART_PATH = "/cart";

    static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    public static final String DEFAULT_CSRF_HEADER_NAME = "X-CSRF-TOKEN";
    public static final String DEFAULT_AUTH_TOKEN_HEADER_NAME = "X-Auth-Token";
    static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = HttpSessionCsrfTokenRepository.class.getName().concat(".CSRF_TOKEN");
    public static final String FB_TOKEN_HEADER_NAME = "X-FB-Token";
 

}