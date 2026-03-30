package com.redsocial.modelo;

import java.util.ArrayList;
import java.util.List;

public class Publicacion {
    private int id;
    private int usuarioId;
    private String usuarioNombre;
    private String usuarioAvatar;

    // Datos principales 
    private String titulo;
    private String duracion;
    private String imagenPrincipal;

    // Datos detallados 
    private String descripcion;
    private List<String> ingredientes;
    private List<String> pasos;
    private List<String> imagenesAdicionales;
    private String dificultad;
    private int calorias;


    private int likes;
    private boolean likedByCurrentUser;

    // Fechas
    private String fechaCreacion;

    // Constructor vacío (OBLIGATORIO para JSON)
    public Publicacion() {
        this.ingredientes = new ArrayList<>();
        this.pasos = new ArrayList<>();
        this.imagenesAdicionales = new ArrayList<>();
    }

    // Constructor para crear nueva receta
    public Publicacion(int usuarioId, String titulo, String duracion, String imagenPrincipal) {
        this.usuarioId = usuarioId;
        this.titulo = titulo;
        this.duracion = duracion;
        this.imagenPrincipal = imagenPrincipal;
        this.ingredientes = new ArrayList<>();
        this.pasos = new ArrayList<>();
        this.imagenesAdicionales = new ArrayList<>();
        this.likes = 0;
        this.likedByCurrentUser = false;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioAvatar() {
        return usuarioAvatar;
    }

    public void setUsuarioAvatar(String usuarioAvatar) {
        this.usuarioAvatar = usuarioAvatar;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getImagenPrincipal() {
        return imagenPrincipal;
    }

    public void setImagenPrincipal(String imagenPrincipal) {
        this.imagenPrincipal = imagenPrincipal;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<String> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<String> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<String> getPasos() {
        return pasos;
    }

    public void setPasos(List<String> pasos) {
        this.pasos = pasos;
    }

    public List<String> getImagenesAdicionales() {
        return imagenesAdicionales;
    }

    public void setImagenesAdicionales(List<String> imagenesAdicionales) {
        this.imagenesAdicionales = imagenesAdicionales;
    }

    public String getDificultad() {
        return dificultad;
    }

    public void setDificultad(String dificultad) {
        this.dificultad = dificultad;
    }

    public int getCalorias() {
        return calorias;
    }

    public void setCalorias(int calorias) {
        this.calorias = calorias;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public boolean isLikedByCurrentUser() {
        return likedByCurrentUser;
    }

    public void setLikedByCurrentUser(boolean likedByCurrentUser) {
        this.likedByCurrentUser = likedByCurrentUser;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    // Clase para respuestas de lista (vista previa)
    // Clase para respuestas de lista (vista previa)
    public static class PublicacionPreview {
        private int id;
        private int usuarioId; // ← AÑADIR ESTO
        private String usuarioNombre;
        private String titulo;
        private String duracion;
        private String imagenPrincipal;
        private int likes;
        private boolean likedByCurrentUser;

        public PublicacionPreview(Publicacion p) {
            this.id = p.id;
            this.usuarioId = p.usuarioId; // ← AÑADIR ESTO
            this.usuarioNombre = p.usuarioNombre;
            this.titulo = p.titulo;
            this.duracion = p.duracion;
            this.imagenPrincipal = p.imagenPrincipal;
            this.likes = p.likes;
            this.likedByCurrentUser = p.likedByCurrentUser;
        }

        // Getters
        public int getId() {
            return id;
        }

        public int getUsuarioId() {
            return usuarioId;
        } // ← AÑADIR ESTO

        public String getUsuarioNombre() {
            return usuarioNombre;
        }

        public String getTitulo() {
            return titulo;
        }

        public String getDuracion() {
            return duracion;
        }

        public String getImagenPrincipal() {
            return imagenPrincipal;
        }

        public int getLikes() {
            return likes;
        }

        public boolean isLikedByCurrentUser() {
            return likedByCurrentUser;
        }
    }
}