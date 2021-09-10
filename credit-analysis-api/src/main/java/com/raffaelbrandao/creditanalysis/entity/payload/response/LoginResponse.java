package com.raffaelbrandao.creditanalysis.entity.payload.response;

import javax.validation.constraints.NotNull;

public class LoginResponse {
    @NotNull
    private final String type;
    @NotNull
    private final String token;

    public LoginResponse(String type, String token) {
        this.type = type;
        this.token = token;
    }

    public String getType() {
        return type;
    }

    public String getToken() {
        return token;
    }
}
