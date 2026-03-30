package com.example.gostar3;

public class LoginResponse {
    private String token;
    private Usuario usuario;
    private String mensaje;

    public String getToken() { return token; }
    public Usuario getUsuario() { return usuario; }
    public String getMensaje() { return mensaje; }
}