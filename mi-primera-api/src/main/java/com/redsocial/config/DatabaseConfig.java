package com.redsocial.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DatabaseConfig {
    
    private static HikariDataSource dataSource;
    
    static {
        try {
            // Configuración del pool de conexiones
            HikariConfig config = new HikariConfig();
            
            // CAMBIA ESTOS DATOS SEGÚN TU CONFIGURACIÓN
            config.setJdbcUrl("jdbc:mysql://localhost:3306/red_social?useSSL=false&serverTimezone=UTC");
            config.setUsername("root");        // Tu usuario de MySQL
            config.setPassword("");            // Tu contraseña de MySQL
            config.setDriverClassName("com.mysql.cj.jdbc.Driver");
            
            // Configuración del pool
            config.setMaximumPoolSize(10);
            config.setMinimumIdle(5);
            config.setConnectionTimeout(30000);
            config.setIdleTimeout(600000);
            
            dataSource = new HikariDataSource(config);
            
            // Crear tabla si no existe
          
            
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error configurando la base de datos", e);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
        
   
    
    public static void closePool() {
        if (dataSource != null && !dataSource.isClosed()) {
            dataSource.close();
        }
    }
}