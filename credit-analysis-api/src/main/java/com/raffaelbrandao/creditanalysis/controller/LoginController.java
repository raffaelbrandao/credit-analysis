package com.raffaelbrandao.creditanalysis.controller;

import com.raffaelbrandao.creditanalysis.entity.payload.request.LoginRequest;
import com.raffaelbrandao.creditanalysis.entity.payload.response.LoginResponse;
import com.raffaelbrandao.creditanalysis.service.security.CustomUserDetailsService;
import com.raffaelbrandao.creditanalysis.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final TokenService tokenService;

    @Autowired
    public LoginController(AuthenticationManager authenticationManager,
                           CustomUserDetailsService customUserDetailsService, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.tokenService = tokenService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        return new LoginResponse("Bearer", tokenService.create(authentication));
    }
}
