package com.ums.payload;

public class JwtResponse {
    private String type = "Bearer";

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) {
        Token = token;
    }

    private String Token;
}
