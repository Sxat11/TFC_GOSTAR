package com.example.gostar;

import android.graphics.drawable.Drawable;
import android.media.Image;

public class Receta {

    String nombre;
    String duracion;

    int fotoPortada;
//    String[] categoria;
//    String usuario;

    public String getNombre() {
        return nombre;
    }

    public String getDuracion() {
        return duracion;
    }

    public int getFotoPortada() {
        return fotoPortada;
    }
//
//    public String getUsuario() {
//        return usuario;
//    }
//
//    public String[] getCategoria() {
//        return categoria;
//    }

    public Receta(String nombre, String duracion, int fotoPortada){
        this.nombre = nombre;
        this.duracion = duracion;
        this.fotoPortada = fotoPortada;
    }
}
