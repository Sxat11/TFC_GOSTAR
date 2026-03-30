package com.redsocial.servicio;

import com.redsocial.modelo.Usuario;
import com.redsocial.modelo.Usuario.UsuarioDTO;
import com.redsocial.modelo.Usuario.LoginRequest;
import com.redsocial.modelo.Usuario.LoginResponse;
import com.redsocial.config.DatabaseConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.sql.*;

@Path("/auth")
public class AuthService {


    private static Map<String, String> tokensActivos = new HashMap<>(); // token -> username

   
    public static String getUsernameFromToken(String token) {
        return tokensActivos.get(token);
    }

   
    public Usuario validarToken(String token) {
        if (token == null || token.isEmpty()) {
            return null;
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        String username = tokensActivos.get(token);
        if (username == null) {
            return null;
        }

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConfig.getConnection();
            String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = true";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Usuario.fromResultSet(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
        return null;
    }

    // Método para hashear contraseñas
    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }

    // Endpoint: Registro de nuevo usuario
    @POST
    @Path("/registro")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response registrar(Usuario nuevoUsuario) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Validaciones básicas
            if (nuevoUsuario.getUsername() == null || nuevoUsuario.getUsername().trim().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("El username es obligatorio"))
                        .build();
            }

            if (nuevoUsuario.getEmail() == null || nuevoUsuario.getEmail().trim().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("El email es obligatorio"))
                        .build();
            }

            if (nuevoUsuario.getPasswordHash() == null || nuevoUsuario.getPasswordHash().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("La contraseña es obligatoria"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();

            // Verificar si el usuario ya existe
            String checkSql = "SELECT id FROM usuarios WHERE username = ? OR email = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setString(1, nuevoUsuario.getUsername());
            stmt.setString(2, nuevoUsuario.getEmail());
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Response.status(Status.CONFLICT)
                        .entity(new ErrorResponse("El username o email ya está registrado"))
                        .build();
            }
            rs.close();
            stmt.close();

   
            String passwordHash = hashPassword(nuevoUsuario.getPasswordHash());

            // Insertar nuevo usuario
            String insertSql = "INSERT INTO usuarios (username, email, password_hash, nombre, bio, avatar_url, fecha_registro, activo) VALUES (?, ?, ?, ?, ?, ?, NOW(), true)";

            stmt = conn.prepareStatement(insertSql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nuevoUsuario.getUsername());
            stmt.setString(2, nuevoUsuario.getEmail());
            stmt.setString(3, passwordHash);
            stmt.setString(4, nuevoUsuario.getNombre());
            stmt.setString(5, nuevoUsuario.getBio());
            stmt.setString(6, nuevoUsuario.getAvatarUrl());

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new ErrorResponse("Error al crear el usuario"))
                        .build();
            }

           
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nuevoUsuario.setId(rs.getInt(1));
            }
            rs.close();
            stmt.close();

            // Recuperar el usuario completo de la BD
            String selectSql = "SELECT * FROM usuarios WHERE id = ?";
            stmt = conn.prepareStatement(selectSql);
            stmt.setInt(1, nuevoUsuario.getId());
            rs = stmt.executeQuery();

            Usuario usuarioGuardado = null;
            if (rs.next()) {
                usuarioGuardado = Usuario.fromResultSet(rs);
            }

            if (usuarioGuardado == null) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new ErrorResponse("Error al recuperar el usuario"))
                        .build();
            }

            // Generar token automático
            String token = UUID.randomUUID().toString();
            tokensActivos.put(token, usuarioGuardado.getUsername());

            // Actualizar último acceso
            String updateSql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setInt(1, usuarioGuardado.getId());
            stmt.executeUpdate();

            UsuarioDTO usuarioDTO = new UsuarioDTO(usuarioGuardado);
            LoginResponse response = new LoginResponse(token, usuarioDTO, "Registro exitoso");

            return Response.status(Status.CREATED).entity(response).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                    .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de seguridad: " + e.getMessage()))
                    .build();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // Endpoint: Login
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest loginRequest) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            if (loginRequest.getUsernameOrEmail() == null || loginRequest.getPassword() == null) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Usuario/Email y contraseña son obligatorios"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();

            // Buscar usuario por username o email
            String sql = "SELECT * FROM usuarios WHERE (username = ? OR email = ?) AND activo = true";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, loginRequest.getUsernameOrEmail());
            stmt.setString(2, loginRequest.getUsernameOrEmail());
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Usuario no encontrado"))
                        .build();
            }

            Usuario usuario = Usuario.fromResultSet(rs);
            rs.close();
            stmt.close();

            // Verificar contraseña
            String passwordHash = hashPassword(loginRequest.getPassword());
            if (!passwordHash.equals(usuario.getPasswordHash())) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Contraseña incorrecta"))
                        .build();
            }

            // Generar token
            String token = UUID.randomUUID().toString();
            tokensActivos.put(token, usuario.getUsername());

            // Actualizar último acceso
            String updateSql = "UPDATE usuarios SET ultimo_acceso = NOW() WHERE id = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setInt(1, usuario.getId());
            stmt.executeUpdate();

            UsuarioDTO usuarioDTO = new UsuarioDTO(usuario);
            LoginResponse response = new LoginResponse(token, usuarioDTO, "Login exitoso");

            return Response.ok(response).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error: " + e.getMessage()))
                    .build();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // Endpoint: Logout
    @POST
    @Path("/logout")
    @Produces(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("Authorization") String token) {
        if (token == null || token.isEmpty()) {
            return Response.status(Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Token no proporcionado"))
                    .build();
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (tokensActivos.containsKey(token)) {
            tokensActivos.remove(token);
            return Response.ok(new MensajeResponse("Logout exitoso")).build();
        } else {
            return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido"))
                    .build();
        }
    }

    // Endpoint: Verificar token
    @GET
    @Path("/verificar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response verificarToken(@HeaderParam("Authorization") String token) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        if (token == null || token.isEmpty()) {
            return Response.status(Status.UNAUTHORIZED)
                    .entity(new VerificacionResponse(false, null))
                    .build();
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (tokensActivos.containsKey(token)) {
            String username = tokensActivos.get(token);

            try {
                conn = DatabaseConfig.getConnection();
                String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = true";
                stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                rs = stmt.executeQuery();

                if (rs.next()) {
                    Usuario usuario = Usuario.fromResultSet(rs);
                    return Response.ok(new VerificacionResponse(true, new UsuarioDTO(usuario))).build();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                try { if (rs != null) rs.close(); } catch (SQLException e) {}
                try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
                try { if (conn != null) conn.close(); } catch (SQLException e) {}
            }
        }

        return Response.status(Status.UNAUTHORIZED)
                .entity(new VerificacionResponse(false, null))
                .build();
    }

    // Endpoint: Obtener perfil
    @GET
    @Path("/perfil")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPerfil(@HeaderParam("Authorization") String token) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        // Verificar token
        if (token == null || token.isEmpty()) {
            return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token no proporcionado"))
                    .build();
        }

        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        if (!tokensActivos.containsKey(token)) {
            return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido"))
                    .build();
        }

        String username = tokensActivos.get(token);

        try {
            conn = DatabaseConfig.getConnection();
            String sql = "SELECT * FROM usuarios WHERE username = ? AND activo = true";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            rs = stmt.executeQuery();

            if (rs.next()) {
                Usuario usuario = Usuario.fromResultSet(rs);
                return Response.ok(new UsuarioDTO(usuario)).build();
            } else {
                return Response.status(Status.NOT_FOUND)
                        .entity(new ErrorResponse("Usuario no encontrado"))
                        .build();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos"))
                    .build();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // 1. ACTUALIZAR PERFIL
    @PUT
    @Path("/perfil")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarPerfil(@HeaderParam("Authorization") String token,
                                     Usuario usuarioActualizado) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        
        try {
            // Validar token y obtener usuario actual
            Usuario usuarioActual = validarToken(token);
            
            if (usuarioActual == null) {
                return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido"))
                    .build();
            }
            
            int usuarioId = usuarioActual.getId();
            conn = DatabaseConfig.getConnection();
            
            // Validar datos
            if (usuarioActualizado.getEmail() != null && !usuarioActualizado.getEmail().isEmpty()) {
                // Verificar que el email no esté usado por otro usuario
                String checkEmailSql = "SELECT id FROM usuarios WHERE email = ? AND id != ?";
                stmt = conn.prepareStatement(checkEmailSql);
                stmt.setString(1, usuarioActualizado.getEmail());
                stmt.setInt(2, usuarioId);
                rs = stmt.executeQuery();
                
                if (rs.next()) {
                    return Response.status(Status.CONFLICT)
                        .entity(new ErrorResponse("El email ya está en uso por otro usuario"))
                        .build();
                }
                rs.close();
                stmt.close();
            }
            
            // Construir la consulta SQL dinámicamente (solo actualizar campos no nulos)
            StringBuilder sql = new StringBuilder("UPDATE usuarios SET ");
            List<Object> parametros = new ArrayList<>();
            boolean primero = true;
            
            if (usuarioActualizado.getNombre() != null) {
                sql.append(primero ? "" : ", ").append("nombre = ?");
                parametros.add(usuarioActualizado.getNombre());
                primero = false;
            }
            
            if (usuarioActualizado.getEmail() != null) {
                sql.append(primero ? "" : ", ").append("email = ?");
                parametros.add(usuarioActualizado.getEmail());
                primero = false;
            }
            
            if (usuarioActualizado.getBio() != null) {
                sql.append(primero ? "" : ", ").append("bio = ?");
                parametros.add(usuarioActualizado.getBio());
                primero = false;
            }
            
            if (usuarioActualizado.getAvatarUrl() != null) {
                sql.append(primero ? "" : ", ").append("avatar_url = ?");
                parametros.add(usuarioActualizado.getAvatarUrl());
                primero = false;
            }
            
            // Si no hay nada que actualizar
            if (primero) {
                return Response.status(Status.BAD_REQUEST)
                    .entity(new ErrorResponse("No hay datos para actualizar"))
                    .build();
            }
            
            sql.append(" WHERE id = ?");
            parametros.add(usuarioId);
            
            stmt = conn.prepareStatement(sql.toString());
            
            for (int i = 0; i < parametros.size(); i++) {
                stmt.setObject(i + 1, parametros.get(i));
            }
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al actualizar el perfil"))
                    .build();
            }
            
            // Obtener el usuario actualizado
            String selectSql = "SELECT * FROM usuarios WHERE id = ?";
            stmt = conn.prepareStatement(selectSql);
            stmt.setInt(1, usuarioId);
            rs = stmt.executeQuery();
            
            Usuario usuarioActualizadoCompleto = null;
            if (rs.next()) {
                usuarioActualizadoCompleto = Usuario.fromResultSet(rs);
            }
            
            return Response.ok(new UsuarioDTO(usuarioActualizadoCompleto)).build();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                .build();
        } finally {
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // 2. SUBIR AVATAR (versión con URL)
    @POST
    @Path("/avatar")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarAvatar(@HeaderParam("Authorization") String token,
                                     AvatarRequest avatarRequest) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Validar token y obtener usuario actual
            Usuario usuarioActual = validarToken(token);
            
            if (usuarioActual == null) {
                return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido"))
                    .build();
            }
            
            if (avatarRequest.getAvatarUrl() == null || avatarRequest.getAvatarUrl().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                    .entity(new ErrorResponse("URL de avatar no proporcionada"))
                    .build();
            }
            
            conn = DatabaseConfig.getConnection();
            
            String sql = "UPDATE usuarios SET avatar_url = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, avatarRequest.getAvatarUrl());
            stmt.setInt(2, usuarioActual.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al actualizar el avatar"))
                    .build();
            }
            
            return Response.ok(new MensajeResponse("Avatar actualizado correctamente")).build();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                .build();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // 3. CAMBIAR CONTRASEÑA
    @POST
    @Path("/cambiar-password")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarPassword(@HeaderParam("Authorization") String token,
                                    PasswordChangeRequest passwordRequest) {
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            // Validar token y obtener usuario actual
            Usuario usuarioActual = validarToken(token);
            
            if (usuarioActual == null) {
                return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Token inválido"))
                    .build();
            }
            
            // Validar datos
            if (passwordRequest.getCurrentPassword() == null || passwordRequest.getCurrentPassword().isEmpty() ||
                passwordRequest.getNewPassword() == null || passwordRequest.getNewPassword().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                    .entity(new ErrorResponse("Contraseña actual y nueva son obligatorias"))
                    .build();
            }
            
            // Verificar contraseña actual
            String currentHash = hashPassword(passwordRequest.getCurrentPassword());
            
            if (!currentHash.equals(usuarioActual.getPasswordHash())) {
                return Response.status(Status.UNAUTHORIZED)
                    .entity(new ErrorResponse("Contraseña actual incorrecta"))
                    .build();
            }
            
            // Actualizar contraseña
            String newHash = hashPassword(passwordRequest.getNewPassword());
            
            conn = DatabaseConfig.getConnection();
            
            String sql = "UPDATE usuarios SET password_hash = ? WHERE id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newHash);
            stmt.setInt(2, usuarioActual.getId());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows == 0) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al cambiar la contraseña"))
                    .build();
            }
            
            return Response.ok(new MensajeResponse("Contraseña actualizada correctamente")).build();
            
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                .build();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error de seguridad"))
                .build();
        } finally {
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    @DELETE
@Path("/usuario")
@Produces(MediaType.APPLICATION_JSON)
public Response eliminarCuenta(@HeaderParam("Authorization") String token) {
    Connection conn = null;
    PreparedStatement stmt = null;
    
    try {
        Usuario usuario = validarToken(token);
        
        if (usuario == null) {
            return Response.status(Status.UNAUTHORIZED)
                .entity(new ErrorResponse("Token inválido"))
                .build();
        }
        
        conn = DatabaseConfig.getConnection();
        
        // Eliminar usuario (las tablas relacionadas se borrarán en cascada)
        String sql = "DELETE FROM usuarios WHERE id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, usuario.getId());
        
        int affectedRows = stmt.executeUpdate();
        
        if (affectedRows == 0) {
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse("Error al eliminar la cuenta"))
                .build();
        }
        
        // Eliminar token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        tokensActivos.remove(token);
        
        return Response.ok(new MensajeResponse("Cuenta eliminada correctamente")).build();
        
    } catch (SQLException e) {
        e.printStackTrace();
        return Response.status(Status.INTERNAL_SERVER_ERROR)
            .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
            .build();
    } finally {
        try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
        try { if (conn != null) conn.close(); } catch (SQLException e) {}
    }
}

    // Clases auxiliares
    public static class ErrorResponse {
        private String error;
        private long timestamp;

        public ErrorResponse(String error) {
            this.error = error;
            this.timestamp = System.currentTimeMillis();
        }

        public String getError() { return error; }
        public long getTimestamp() { return timestamp; }
    }

    public static class MensajeResponse {
        private String mensaje;
        private long timestamp;

        public MensajeResponse(String mensaje) {
            this.mensaje = mensaje;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMensaje() { return mensaje; }
        public long getTimestamp() { return timestamp; }
    }

    public static class VerificacionResponse {
        private boolean valido;
        private UsuarioDTO usuario;

        public VerificacionResponse(boolean valido, UsuarioDTO usuario) {
            this.valido = valido;
            this.usuario = usuario;
        }

        public boolean isValido() { return valido; }
        public UsuarioDTO getUsuario() { return usuario; }
    }

    public static class AvatarRequest {
        private String avatarUrl;
        
        public String getAvatarUrl() { return avatarUrl; }
        public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    }

    public static class PasswordChangeRequest {
        private String currentPassword;
        private String newPassword;
        
        public String getCurrentPassword() { return currentPassword; }
        public void setCurrentPassword(String currentPassword) { this.currentPassword = currentPassword; }
        public String getNewPassword() { return newPassword; }
        public void setNewPassword(String newPassword) { this.newPassword = newPassword; }
    }
}