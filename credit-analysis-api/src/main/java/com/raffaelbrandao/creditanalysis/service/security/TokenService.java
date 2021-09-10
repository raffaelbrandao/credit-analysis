package com.raffaelbrandao.creditanalysis.service.security;

import org.springframework.security.core.Authentication;

import javax.servlet.http.HttpServletRequest;

public interface TokenService {
    void authenticate(HttpServletRequest request);

    String create(Authentication authentication);
}
