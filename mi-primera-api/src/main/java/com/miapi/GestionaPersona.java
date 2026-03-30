package com.miapi;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/persona")
public class GestionaPersona {
    static Persona p;

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Persona leer() {

        Persona p = new Persona();
        p.setNombre("Lucas");
        p.setSexo("hombre");
        p.setId(33);
        p.setCasado(false);
        return p;
    }

    @POST
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Persona guardar(Persona p) {
        return p;

    }

} 
