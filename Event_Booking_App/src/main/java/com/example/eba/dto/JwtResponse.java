package com.example.eba.dto;

public class JwtResponse {
    private String accessToken;
    private String expire;

    public JwtResponse(String accessToken, String expire) {
        this.accessToken = accessToken;
        this.expire = expire;
    }

    // getters / setters
    public String getAccessToken() { return accessToken; }
    public void setAccessToken(String accessToken) { this.accessToken = accessToken; }

    public String getExpire() { return expire; }
    public void setExpire(String expire) { this.expire = expire; }
}
