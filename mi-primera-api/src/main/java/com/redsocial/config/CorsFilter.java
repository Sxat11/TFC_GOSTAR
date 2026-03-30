package com.redsocial.config;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;

@Provider
public class CorsFilter implements ContainerResponseFilter {
    
    @Override
    public void filter(ContainerRequestContext requestContext, 
                       ContainerResponseContext responseContext) throws IOException {
        
        // Permitir cualquier origen (para desarrollo)
        responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
        
        // Permitir métodos
        responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
        
        // Permitir headers
        responseContext.getHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization");
        
        // Exponer headers
        responseContext.getHeaders().add("Access-Control-Expose-Headers", "Location");
        
        // Permitir credenciales
        responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
    }
}