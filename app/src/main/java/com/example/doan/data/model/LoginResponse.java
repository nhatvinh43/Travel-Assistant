package com.example.doan.data.model;

public class LoginResponse {
    private int userId;
    private String token;

    public LoginResponse(int userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public int getUserId() {
        return userId;
    }

    public String getToken() {
        return token;
    }
}
