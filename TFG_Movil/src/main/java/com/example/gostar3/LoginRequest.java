package com.example.gostar3;

public class LoginRequest {
    private String usernameOrEmail;
    private String password;

    public LoginRequest(String usernameOrEmail, String password) {
        this.usernameOrEmail = usernameOrEmail;
        this.password = password;
    }

    public String getUsernameOrEmail() { return usernameOrEmail; }
    public String getPassword() { return password; }
}