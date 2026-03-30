package com.redsocial.modelo;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.Date;

@XmlRootElement
public class Usuario {
    private int id;
    private String username;
    private String email;
    private String passwordHash; // No se enviará en respuestas
    private String nombre;
    private String bio;
    private String avatarUrl;
    private Date fechaRegistro;
    private Date ultimoAcceso;
    private boolean activo;

    
    public Usuario() {
    }

    
    public Usuario(String username, String email, String passwordHash, String nombre) {
        this.username = username;
        this.email = email;
        this.passwordHash = passwordHash;
        this.nombre = nombre;
        this.fechaRegistro = new Date();
        this.activo = true;
    }

    
    public static class UsuarioDTO {
        private int id;
        private String username;
        private String email;
        private String nombre;
        private String bio;
        private String avatarUrl;
        private Date fechaRegistro;

        
        public UsuarioDTO(Usuario u) {
            this.id = u.id;
            this.username = u.username;
            this.email = u.email;
            this.nombre = u.nombre;
            this.bio = u.bio;
            this.avatarUrl = u.avatarUrl;
            this.fechaRegistro = u.fechaRegistro;
        }

        // Getters y setters
        public int getId() {
            return id;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getNombre() {
            return nombre;
        }

        public String getBio() {
            return bio;
        }

        public String getAvatarUrl() {
            return avatarUrl;
        }

        public Date getFechaRegistro() {
            return fechaRegistro;
        }
    }

    
    public static class LoginRequest {
        private String usernameOrEmail;
        private String password;

        public String getUsernameOrEmail() {
            return usernameOrEmail;
        }

        public void setUsernameOrEmail(String usernameOrEmail) {
            this.usernameOrEmail = usernameOrEmail;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    
    public static class LoginResponse {
        private String token;
        private UsuarioDTO usuario;
        private String mensaje;

        public LoginResponse(String token, UsuarioDTO usuario, String mensaje) {
            this.token = token;
            this.usuario = usuario;
            this.mensaje = mensaje;
        }

        
        public String getToken() {
            return token;
        }

        public UsuarioDTO getUsuario() {
            return usuario;
        }

        public String getMensaje() {
            return mensaje;
        }
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public Date getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(Date fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public Date getUltimoAcceso() {
        return ultimoAcceso;
    }

    public void setUltimoAcceso(Date ultimoAcceso) {
        this.ultimoAcceso = ultimoAcceso;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    // Método para convertir ResultSet a Usuario
    public static Usuario fromResultSet(java.sql.ResultSet rs) throws java.sql.SQLException {
        Usuario u = new Usuario();
        u.setId(rs.getInt("id"));
        u.setUsername(rs.getString("username"));
        u.setEmail(rs.getString("email"));
        u.setPasswordHash(rs.getString("password_hash"));
        u.setNombre(rs.getString("nombre"));
        u.setBio(rs.getString("bio"));
        u.setAvatarUrl(rs.getString("avatar_url"));
        u.setFechaRegistro(rs.getTimestamp("fecha_registro"));
        u.setUltimoAcceso(rs.getTimestamp("ultimo_acceso"));
        u.setActivo(rs.getBoolean("activo"));
        return u;
    }
}