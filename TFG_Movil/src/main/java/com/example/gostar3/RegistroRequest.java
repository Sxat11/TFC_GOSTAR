package com.example.gostar3;

public class RegistroRequest {
    private String username;
    private String email;
    private String passwordHash;
    private String nombre;
    private String bio;

    public RegistroRequest(String username, String email, String password,
                           String nombre, String bio) {
        this.username = username;
        this.email = email;
        this.passwordHash = password;
        this.nombre = nombre;
        this.bio = bio;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getPasswordHash() { return passwordHash; }
    public String getNombre() { return nombre; }
    public String getBio() { return bio; }
}