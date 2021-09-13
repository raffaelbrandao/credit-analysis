package com.raffaelbrandao.creditanalysis.service.security.filter;

import com.raffaelbrandao.creditanalysis.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;

    @Autowired
    public CustomAuthenticationFilter(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if (!request.getServletPath().equals("/login")) {
            tokenService.authenticate(request);
        }

        filterChain.doFilter(request, response);
    }
}
