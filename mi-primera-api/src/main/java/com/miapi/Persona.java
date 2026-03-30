package com.miapi;

import java.io.Serializable;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement

public class Persona implements Serializable {
    private int id;
    private String nombre;
    private boolean casado;
    private String sexo;
    

    public Persona() {
    }
    public Persona(int id,String nombre,String sexo,boolean casado) {
        super();
        this.id = id;
        this.nombre = nombre;
        this.casado = casado;
        this.sexo = sexo;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
    
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getSexo() {
        return sexo;
    }
    public void setCasado(boolean casado){
        this.casado = casado;
    }
    public boolean getCasado(){
        return casado;
    }
    

}