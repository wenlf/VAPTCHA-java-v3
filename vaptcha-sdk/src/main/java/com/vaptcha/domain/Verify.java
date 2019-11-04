package com.vaptcha.domain;

public class Verify {
    private String token;

    public Verify() {
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Verify(String token) {
        this.token = token;
    }
}
