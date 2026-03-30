package com.redsocial.servicio;

import com.redsocial.modelo.Publicacion;
import com.redsocial.modelo.Usuario;
import com.redsocial.config.DatabaseConfig;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import jakarta.ws.rs.core.Response.Status;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Path("/publicaciones")
public class PublicacionService {

    // Clase para respuestas de error
    public static class ErrorResponse {
        private String error;
        private long timestamp;

        public ErrorResponse(String error) {
            this.error = error;
            this.timestamp = System.currentTimeMillis();
        }

        public String getError() {
            return error;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    // MÉTODO CORREGIDO: Obtener usuario desde token USANDO AuthService
    private Usuario obtenerUsuarioDesdeToken(String token) {
        if (token == null || token.isEmpty()) {
            System.out.println("Token nulo o vacío");
            return null;
        }

        // Quitar "Bearer " del token
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        System.out.println("Token limpio: " + token);

        // 1. Obtener username del token usando AuthService
        String username = com.redsocial.servicio.AuthService.getUsernameFromToken(token);

        System.out.println("Username obtenido de AuthService: " + username);

        if (username == null) {
            System.out.println("Username no encontrado para el token");
            return null;
        }

        // 2. Buscar usuario en BD por username
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
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setUsername(rs.getString("username"));
                usuario.setEmail(rs.getString("email"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setAvatarUrl(rs.getString("avatar_url"));
                System.out.println(
                        "Usuario encontrado en BD: ID=" + usuario.getId() + ", username=" + usuario.getUsername());
                return usuario;
            } else {
                System.out.println("Usuario no encontrado en BD para username: " + username);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }

        return null;
    }

    // Validar token
    public Usuario validarToken(String token) {
        return obtenerUsuarioDesdeToken(token);
    }

    // 1. CREAR nueva publicación
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearPublicacion(@HeaderParam("Authorization") String token,
            Publicacion nuevaPublicacion) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        System.out.println("=== CREAR PUBLICACIÓN ===");
        System.out.println("Token recibido: " + token);

        try {
            // Validar token y obtener usuario
            Usuario usuario = validarToken(token);

            System.out.println("Usuario obtenido: "
                    + (usuario != null ? usuario.getId() + " - " + usuario.getUsername() : "null"));

            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Token inválido"))
                        .build();
            }

            int usuarioId = usuario.getId();
            System.out.println("Usuario ID a insertar: " + usuarioId);

            // Validar datos mínimos
            if (nuevaPublicacion.getTitulo() == null || nuevaPublicacion.getTitulo().trim().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("El título es obligatorio"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();

            // Insertar publicación
            String sql = "INSERT INTO publicaciones (usuario_id, titulo, duracion, imagen_principal, " +
                    "descripcion, dificultad, calorias) VALUES (?, ?, ?, ?, ?, ?, ?)";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setInt(1, usuarioId);
            stmt.setString(2, nuevaPublicacion.getTitulo());
            stmt.setString(3, nuevaPublicacion.getDuracion());
            stmt.setString(4, nuevaPublicacion.getImagenPrincipal());
            stmt.setString(5, nuevaPublicacion.getDescripcion());
            stmt.setString(6, nuevaPublicacion.getDificultad());
            stmt.setInt(7, nuevaPublicacion.getCalorias());

            System.out.println("Ejecutando SQL con usuarioId: " + usuarioId);

            int affectedRows = stmt.executeUpdate();
            System.out.println("Filas afectadas: " + affectedRows);

            if (affectedRows == 0) {
                return Response.status(Status.INTERNAL_SERVER_ERROR)
                        .entity(new ErrorResponse("Error al crear la publicación"))
                        .build();
            }

            // Obtener ID generado
            rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                nuevaPublicacion.setId(rs.getInt(1));
                System.out.println("ID generado: " + nuevaPublicacion.getId());
            }
            rs.close();
            stmt.close();

            // Insertar ingredientes
            if (nuevaPublicacion.getIngredientes() != null && !nuevaPublicacion.getIngredientes().isEmpty()) {
                String sqlIng = "INSERT INTO publicacion_ingredientes (publicacion_id, ingrediente) VALUES (?, ?)";
                stmt = conn.prepareStatement(sqlIng);
                for (String ingrediente : nuevaPublicacion.getIngredientes()) {
                    stmt.setInt(1, nuevaPublicacion.getId());
                    stmt.setString(2, ingrediente);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Ingredientes insertados: " + nuevaPublicacion.getIngredientes().size());
                stmt.close();
            }

            // Insertar pasos
            if (nuevaPublicacion.getPasos() != null && !nuevaPublicacion.getPasos().isEmpty()) {
                String sqlPasos = "INSERT INTO publicacion_pasos (publicacion_id, paso, orden) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sqlPasos);
                int orden = 1;
                for (String paso : nuevaPublicacion.getPasos()) {
                    stmt.setInt(1, nuevaPublicacion.getId());
                    stmt.setString(2, paso);
                    stmt.setInt(3, orden++);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Pasos insertados: " + nuevaPublicacion.getPasos().size());
                stmt.close();
            }

            // Insertar imágenes adicionales
            if (nuevaPublicacion.getImagenesAdicionales() != null
                    && !nuevaPublicacion.getImagenesAdicionales().isEmpty()) {
                String sqlImg = "INSERT INTO publicacion_imagenes (publicacion_id, url, orden) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(sqlImg);
                int orden = 1;
                for (String img : nuevaPublicacion.getImagenesAdicionales()) {
                    stmt.setInt(1, nuevaPublicacion.getId());
                    stmt.setString(2, img);
                    stmt.setInt(3, orden++);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Imágenes insertadas: " + nuevaPublicacion.getImagenesAdicionales().size());
            }

            System.out.println("=== PUBLICACIÓN CREADA CON ÉXITO ===\n");
            return Response.status(Status.CREATED).entity(nuevaPublicacion).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // 2. OBTENER TODAS las publicaciones (feed)
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicaciones(@HeaderParam("Authorization") String token,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("10") int limit) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Validar usuario para saber sus likes
            Usuario usuarioActual = validarToken(token);
            conn = DatabaseConfig.getConnection();
            int offset = (page - 1) * limit;

            String sql = "SELECT p.*, u.username as autor_nombre, u.avatar_url as autor_avatar " +
                    "FROM publicaciones p " +
                    "JOIN usuarios u ON p.usuario_id = u.id " +
                    "ORDER BY p.fecha_creacion DESC " +
                    "LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, limit);
            stmt.setInt(2, offset);
            rs = stmt.executeQuery();

            List<Publicacion.PublicacionPreview> publicaciones = new ArrayList<>();

            while (rs.next()) {
                Publicacion p = new Publicacion();
                p.setId(rs.getInt("id"));
                p.setUsuarioId(rs.getInt("usuario_id"));
                p.setUsuarioNombre(rs.getString("autor_nombre"));
                p.setUsuarioAvatar(rs.getString("autor_avatar"));
                p.setTitulo(rs.getString("titulo"));
                p.setDuracion(rs.getString("duracion"));
                p.setImagenPrincipal(rs.getString("imagen_principal"));
                p.setLikes(rs.getInt("likes"));
                p.setFechaCreacion(rs.getString("fecha_creacion"));

                // --- LÓGICA PARA CARGAR EL ESTADO DEL LIKE ---
                if (usuarioActual != null) {
                    String checkSql = "SELECT 1 FROM publicacion_likes WHERE usuario_id = ? AND publicacion_id = ?";
                    try (PreparedStatement likeStmt = conn.prepareStatement(checkSql)) {
                        likeStmt.setInt(1, usuarioActual.getId());
                        likeStmt.setInt(2, p.getId());
                        try (ResultSet rsLike = likeStmt.executeQuery()) {
                            p.setLikedByCurrentUser(rsLike.next()); // true si existe el registro
                        }
                    }
                }
                // ---------------------------------------------

                publicaciones.add(new Publicacion.PublicacionPreview(p));
            }
            return Response.ok(publicaciones).build();
        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).build();
        } finally {
            // ... (tus cierres de conexión habituales)
        }
    }

    // 3. OBTENER publicación por ID (detalle)
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicacion(@PathParam("id") int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConfig.getConnection();

            String sql = "SELECT p.*, u.username as autor_nombre, u.avatar_url as autor_avatar " +
                    "FROM publicaciones p " +
                    "JOIN usuarios u ON p.usuario_id = u.id " +
                    "WHERE p.id = ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return Response.status(Status.NOT_FOUND)
                        .entity(new ErrorResponse("Publicación no encontrada"))
                        .build();
            }

            Publicacion p = new Publicacion();
            p.setId(rs.getInt("id"));
            p.setUsuarioId(rs.getInt("usuario_id"));
            p.setUsuarioNombre(rs.getString("autor_nombre"));
            p.setUsuarioAvatar(rs.getString("autor_avatar"));
            p.setTitulo(rs.getString("titulo"));
            p.setDuracion(rs.getString("duracion"));
            p.setImagenPrincipal(rs.getString("imagen_principal"));
            p.setDescripcion(rs.getString("descripcion"));
            p.setDificultad(rs.getString("dificultad"));
            p.setCalorias(rs.getInt("calorias"));
            p.setLikes(rs.getInt("likes"));
            p.setFechaCreacion(rs.getString("fecha_creacion"));

            rs.close();
            stmt.close();

            // Obtener ingredientes
            List<String> ingredientes = new ArrayList<>();
            String sqlIng = "SELECT ingrediente FROM publicacion_ingredientes WHERE publicacion_id = ?";
            stmt = conn.prepareStatement(sqlIng);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                ingredientes.add(rs.getString("ingrediente"));
            }
            p.setIngredientes(ingredientes);

            rs.close();
            stmt.close();

            // Obtener pasos
            List<String> pasos = new ArrayList<>();
            String sqlPasos = "SELECT paso FROM publicacion_pasos WHERE publicacion_id = ? ORDER BY orden";
            stmt = conn.prepareStatement(sqlPasos);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                pasos.add(rs.getString("paso"));
            }
            p.setPasos(pasos);

            rs.close();
            stmt.close();

            // Obtener imágenes adicionales
            List<String> imagenes = new ArrayList<>();
            String sqlImg = "SELECT url FROM publicacion_imagenes WHERE publicacion_id = ? ORDER BY orden";
            stmt = conn.prepareStatement(sqlImg);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                imagenes.add(rs.getString("url"));
            }
            p.setImagenesAdicionales(imagenes);

            return Response.ok(p).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al cargar la publicación"))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // 4. DAR LIKE
    @POST
    @Path("/{id}/like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response darLike(@HeaderParam("Authorization") String token, @PathParam("id") int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Usamos el método que ya tienes definido en tu clase
            Usuario usuario = obtenerUsuarioDesdeToken(token);

            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Sesión expirada o token inválido"))
                        .build();
            }

            int usuarioId = usuario.getId();
            conn = DatabaseConfig.getConnection();

            // 1. Verificar si ya existe el like para no duplicar
            String checkSql = "SELECT 1 FROM publicacion_likes WHERE usuario_id = ? AND publicacion_id = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, id);
            rs = stmt.executeQuery();

            if (rs.next()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("Ya has dado like a esta publicación"))
                        .build();
            }
            stmt.close();

            // 2. Insertar el registro de like
            String likeSql = "INSERT INTO publicacion_likes (usuario_id, publicacion_id) VALUES (?, ?)";
            stmt = conn.prepareStatement(likeSql);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            stmt.close();

            // 3. Incrementar el contador en la tabla publicaciones
            String updateSql = "UPDATE publicaciones SET likes = likes + 1 WHERE id = ?";
            stmt = conn.prepareStatement(updateSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            // 4. Obtener el número actualizado para devolverlo al cliente
            String countSql = "SELECT likes FROM publicaciones WHERE id = ?";
            stmt = conn.prepareStatement(countSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            int nuevosLikes = 0;
            if (rs.next()) {
                nuevosLikes = rs.getInt("likes");
            }

            // Devolvemos el estado completo para que el JS no se pierda
            return Response.ok(new LikeResponse(nuevosLikes, true)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos al dar like")).build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // 5. QUITAR LIKE
    @DELETE
    @Path("/{id}/like")
    @Produces(MediaType.APPLICATION_JSON)
    public Response quitarLike(@HeaderParam("Authorization") String token, @PathParam("id") int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            Usuario usuario = obtenerUsuarioDesdeToken(token);

            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Sesión expirada"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();

            // 1. Borrar el registro del like
            String deleteSql = "DELETE FROM publicacion_likes WHERE usuario_id = ? AND publicacion_id = ?";
            stmt = conn.prepareStatement(deleteSql);
            stmt.setInt(1, usuario.getId());
            stmt.setInt(2, id);
            int filasBorradas = stmt.executeUpdate();
            stmt.close();

            // 2. Solo si realmente existía el like, decrementamos el contador
            if (filasBorradas > 0) {
                String updateSql = "UPDATE publicaciones SET likes = GREATEST(0, likes - 1) WHERE id = ?";
                stmt = conn.prepareStatement(updateSql);
                stmt.setInt(1, id);
                stmt.executeUpdate();
                stmt.close();
            }

            // 3. Obtener contador actual
            String countSql = "SELECT likes FROM publicaciones WHERE id = ?";
            stmt = conn.prepareStatement(countSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            int nuevosLikes = 0;
            if (rs.next()) {
                nuevosLikes = rs.getInt("likes");
            }

            return Response.ok(new LikeResponse(nuevosLikes, false)).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al quitar like")).build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // Clase de respuesta ajustada para que el JS la entienda perfectamente
    public static class LikeResponse {
        private int likes;
        private boolean likedByCurrentUser; // Cambiado para coincidir con el objeto Publicacion

        public LikeResponse(int likes, boolean likedByCurrentUser) {
            this.likes = likes;
            this.likedByCurrentUser = likedByCurrentUser;
        }

        public int getLikes() {
            return likes;
        }

        public boolean isLikedByCurrentUser() {
            return likedByCurrentUser;
        }
    }

    // 6. OBTENER publicaciones de un usuario
    @GET
    @Path("/usuario/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicacionesByUsuario(@PathParam("usuarioId") int usuarioId,
            @QueryParam("page") @DefaultValue("1") int page) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConfig.getConnection();

            int limit = 10;
            int offset = (page - 1) * limit;

            String sql = "SELECT p.*, u.username as autor_nombre, u.avatar_url as autor_avatar " +
                    "FROM publicaciones p " +
                    "JOIN usuarios u ON p.usuario_id = u.id " +
                    "WHERE p.usuario_id = ? " +
                    "ORDER BY p.fecha_creacion DESC " +
                    "LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();

            List<Publicacion.PublicacionPreview> publicaciones = new ArrayList<>();

            while (rs.next()) {
                Publicacion p = new Publicacion();
                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDuracion(rs.getString("duracion"));
                p.setImagenPrincipal(rs.getString("imagen_principal"));
                p.setLikes(rs.getInt("likes"));
                p.setUsuarioNombre(rs.getString("autor_nombre"));

                publicaciones.add(new Publicacion.PublicacionPreview(p));
            }

            return Response.ok(publicaciones).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error al cargar publicaciones del usuario"))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // 7. ELIMINAR publicación (solo el autor puede eliminar)
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarPublicacion(@HeaderParam("Authorization") String token,
            @PathParam("id") int id) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        System.out.println("=== ELIMINAR PUBLICACIÓN ===");
        System.out.println("ID a eliminar: " + id);

        try {
            // Validar token y obtener usuario
            Usuario usuario = validarToken(token);

            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Token inválido"))
                        .build();
            }

            int usuarioId = usuario.getId();
            System.out.println("Usuario que intenta eliminar: " + usuarioId);

            conn = DatabaseConfig.getConnection();

            // Verificar que la publicación existe y pertenece al usuario
            String checkSql = "SELECT usuario_id FROM publicaciones WHERE id = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return Response.status(Status.NOT_FOUND)
                        .entity(new ErrorResponse("Publicación no encontrada"))
                        .build();
            }

            int autorId = rs.getInt("usuario_id");
            rs.close();
            stmt.close();

            if (autorId != usuarioId) {
                System.out.println("¡Usuario no autorizado! Autor real: " + autorId + ", Intento: " + usuarioId);
                return Response.status(Status.FORBIDDEN)
                        .entity(new ErrorResponse("No tienes permiso para eliminar esta publicación"))
                        .build();
            }

            // Eliminar la publicación (las tablas relacionadas se borrarán en cascada)
            String deleteSql = "DELETE FROM publicaciones WHERE id = ?";
            stmt = conn.prepareStatement(deleteSql);
            stmt.setInt(1, id);

            int affectedRows = stmt.executeUpdate();
            System.out.println("Filas eliminadas: " + affectedRows);

            return Response.ok(new MensajeResponse("Publicación eliminada correctamente")).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

    // Clase auxiliar para mensajes
    public static class MensajeResponse {
        private String mensaje;
        private long timestamp;

        public MensajeResponse(String mensaje) {
            this.mensaje = mensaje;
            this.timestamp = System.currentTimeMillis();
        }

        public String getMensaje() {
            return mensaje;
        }

        public long getTimestamp() {
            return timestamp;
        }
    }

    // 8. EDITAR publicación (solo el autor puede editar)
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editarPublicacion(@HeaderParam("Authorization") String token,
            @PathParam("id") int id,
            Publicacion publicacionEditada) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        System.out.println("=== EDITAR PUBLICACIÓN ===");
        System.out.println("ID a editar: " + id);

        try {
            // Validar token y obtener usuario
            Usuario usuario = validarToken(token);

            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Token inválido"))
                        .build();
            }

            int usuarioId = usuario.getId();
            System.out.println("Usuario que intenta editar: " + usuarioId);

            conn = DatabaseConfig.getConnection();

            // Verificar que la publicación existe y pertenece al usuario
            String checkSql = "SELECT usuario_id FROM publicaciones WHERE id = ?";
            stmt = conn.prepareStatement(checkSql);
            stmt.setInt(1, id);
            rs = stmt.executeQuery();

            if (!rs.next()) {
                return Response.status(Status.NOT_FOUND)
                        .entity(new ErrorResponse("Publicación no encontrada"))
                        .build();
            }

            int autorId = rs.getInt("usuario_id");
            rs.close();
            stmt.close();

            if (autorId != usuarioId) {
                System.out.println("¡Usuario no autorizado! Autor real: " + autorId + ", Intento: " + usuarioId);
                return Response.status(Status.FORBIDDEN)
                        .entity(new ErrorResponse("No tienes permiso para editar esta publicación"))
                        .build();
            }

            // Actualizar datos básicos de la publicación
            String updateSql = "UPDATE publicaciones SET titulo = ?, duracion = ?, imagen_principal = ?, " +
                    "descripcion = ?, dificultad = ?, calorias = ? WHERE id = ?";

            stmt = conn.prepareStatement(updateSql);
            stmt.setString(1, publicacionEditada.getTitulo());
            stmt.setString(2, publicacionEditada.getDuracion());
            stmt.setString(3, publicacionEditada.getImagenPrincipal());
            stmt.setString(4, publicacionEditada.getDescripcion());
            stmt.setString(5, publicacionEditada.getDificultad());
            stmt.setInt(6, publicacionEditada.getCalorias());
            stmt.setInt(7, id);

            int affectedRows = stmt.executeUpdate();
            System.out.println("Filas actualizadas (publicación): " + affectedRows);
            stmt.close();

            // Eliminar ingredientes antiguos
            String deleteIngSql = "DELETE FROM publicacion_ingredientes WHERE publicacion_id = ?";
            stmt = conn.prepareStatement(deleteIngSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            // Insertar nuevos ingredientes
            if (publicacionEditada.getIngredientes() != null && !publicacionEditada.getIngredientes().isEmpty()) {
                String insertIngSql = "INSERT INTO publicacion_ingredientes (publicacion_id, ingrediente) VALUES (?, ?)";
                stmt = conn.prepareStatement(insertIngSql);
                for (String ingrediente : publicacionEditada.getIngredientes()) {
                    stmt.setInt(1, id);
                    stmt.setString(2, ingrediente);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Ingredientes actualizados: " + publicacionEditada.getIngredientes().size());
                stmt.close();
            }

            // Eliminar pasos antiguos
            String deletePasosSql = "DELETE FROM publicacion_pasos WHERE publicacion_id = ?";
            stmt = conn.prepareStatement(deletePasosSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            // Insertar nuevos pasos
            if (publicacionEditada.getPasos() != null && !publicacionEditada.getPasos().isEmpty()) {
                String insertPasosSql = "INSERT INTO publicacion_pasos (publicacion_id, paso, orden) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(insertPasosSql);
                int orden = 1;
                for (String paso : publicacionEditada.getPasos()) {
                    stmt.setInt(1, id);
                    stmt.setString(2, paso);
                    stmt.setInt(3, orden++);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Pasos actualizados: " + publicacionEditada.getPasos().size());
                stmt.close();
            }

            // Eliminar imágenes antiguas
            String deleteImgSql = "DELETE FROM publicacion_imagenes WHERE publicacion_id = ?";
            stmt = conn.prepareStatement(deleteImgSql);
            stmt.setInt(1, id);
            stmt.executeUpdate();
            stmt.close();

            // Insertar nuevas imágenes
            if (publicacionEditada.getImagenesAdicionales() != null
                    && !publicacionEditada.getImagenesAdicionales().isEmpty()) {
                String insertImgSql = "INSERT INTO publicacion_imagenes (publicacion_id, url, orden) VALUES (?, ?, ?)";
                stmt = conn.prepareStatement(insertImgSql);
                int orden = 1;
                for (String img : publicacionEditada.getImagenesAdicionales()) {
                    stmt.setInt(1, id);
                    stmt.setString(2, img);
                    stmt.setInt(3, orden++);
                    stmt.addBatch();
                }
                stmt.executeBatch();
                System.out.println("Imágenes actualizadas: " + publicacionEditada.getImagenesAdicionales().size());
            }

            // Obtener la publicación actualizada
            publicacionEditada.setId(id);
            publicacionEditada.setUsuarioId(usuarioId);
            publicacionEditada.setUsuarioNombre(usuario.getNombre());

            System.out.println("=== PUBLICACIÓN EDITADA CON ÉXITO ===\n");
            return Response.ok(publicacionEditada).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }

// 9. BUSCAR recetas por título o descripción
    @GET
    @Path("/buscar")
    @Produces(MediaType.APPLICATION_JSON)
    public Response buscarRecetas(@HeaderParam("Authorization") String token,
            @QueryParam("q") String query,
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("limit") @DefaultValue("20") int limit) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // CORRECCIÓN 1: Usar el nombre de método correcto que tienes en tu clase
            Usuario usuario = obtenerUsuarioDesdeToken(token);
            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Sesión expirada"))
                        .build();
            }

            if (query == null || query.trim().isEmpty()) {
                return Response.status(Status.BAD_REQUEST)
                        .entity(new ErrorResponse("¿Qué quieres buscar?"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();
            int offset = (page - 1) * limit;
            String searchTerm = "%" + query.toLowerCase() + "%";

            // CORRECCIÓN 2: Simplificamos la SQL para evitar errores con tablas que quizás no existan
            // Buscamos en título y descripción.
            String sql = "SELECT p.*, u.nombre as autor_nombre " +
                         "FROM publicaciones p " +
                         "JOIN usuarios u ON p.usuario_id = u.id " +
                         "WHERE LOWER(p.titulo) LIKE ? OR LOWER(p.descripcion) LIKE ? " +
                         "ORDER BY p.fecha_creacion DESC LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, searchTerm);
            stmt.setString(2, searchTerm);
            stmt.setInt(3, limit);
            stmt.setInt(4, offset);
            rs = stmt.executeQuery();

            List<Publicacion.PublicacionPreview> resultados = new ArrayList<>();

            while (rs.next()) {
                Publicacion p = new Publicacion();
                p.setId(rs.getInt("id"));
                p.setUsuarioId(rs.getInt("usuario_id"));
                p.setUsuarioNombre(rs.getString("autor_nombre"));
                p.setTitulo(rs.getString("titulo"));
                p.setDuracion(rs.getString("duracion"));
                p.setImagenPrincipal(rs.getString("imagen_principal"));
                p.setLikes(rs.getInt("likes"));
                p.setFechaCreacion(rs.getString("fecha_creacion"));

                // CORRECCIÓN 3: Verificar el Like (Copiado de tu método listar)
                String checkLikeSql = "SELECT 1 FROM publicacion_likes WHERE usuario_id = ? AND publicacion_id = ?";
                try (PreparedStatement likeStmt = conn.prepareStatement(checkLikeSql)) {
                    likeStmt.setInt(1, usuario.getId());
                    likeStmt.setInt(2, p.getId());
                    try (ResultSet likeRs = likeStmt.executeQuery()) {
                        p.setLikedByCurrentUser(likeRs.next());
                    }
                }

                resultados.add(new Publicacion.PublicacionPreview(p));
            }

            return Response.ok(resultados).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error en la búsqueda")).build();
        } finally {
            // Cierre seguro de recursos
            try { if (rs != null) rs.close(); } catch (SQLException e) {}
            try { if (stmt != null) stmt.close(); } catch (SQLException e) {}
            try { if (conn != null) conn.close(); } catch (SQLException e) {}
        }
    }

    // 9. OBTENER publicaciones que ha dado like un usuario (favoritos)
    @GET
    @Path("/liked/{usuarioId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPublicacionesLikedByUser(@HeaderParam("Authorization") String token,
            @PathParam("usuarioId") int usuarioId,
            @QueryParam("page") @DefaultValue("1") int page) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            // Validar token
            Usuario usuario = validarToken(token);
            if (usuario == null) {
                return Response.status(Status.UNAUTHORIZED)
                        .entity(new ErrorResponse("Token inválido"))
                        .build();
            }

            conn = DatabaseConfig.getConnection();

            int limit = 10;
            int offset = (page - 1) * limit;

            String sql = "SELECT p.*, u.username as autor_nombre, u.avatar_url as autor_avatar " +
                    "FROM publicaciones p " +
                    "JOIN usuarios u ON p.usuario_id = u.id " +
                    "JOIN publicacion_likes l ON p.id = l.publicacion_id " +
                    "WHERE l.usuario_id = ? " +
                    "ORDER BY l.fecha DESC " +
                    "LIMIT ? OFFSET ?";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, usuarioId);
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            rs = stmt.executeQuery();

            List<Publicacion.PublicacionPreview> publicaciones = new ArrayList<>();

            while (rs.next()) {
                Publicacion p = new Publicacion();
                p.setId(rs.getInt("id"));
                p.setTitulo(rs.getString("titulo"));
                p.setDuracion(rs.getString("duracion"));
                p.setImagenPrincipal(rs.getString("imagen_principal"));
                p.setLikes(rs.getInt("likes"));
                p.setUsuarioNombre(rs.getString("autor_nombre"));
                p.setLikedByCurrentUser(true); // Porque el usuario dio like
                p.setFechaCreacion(rs.getString("fecha_creacion"));

                publicaciones.add(new Publicacion.PublicacionPreview(p));
            }

            return Response.ok(publicaciones).build();

        } catch (SQLException e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR)
                    .entity(new ErrorResponse("Error de base de datos: " + e.getMessage()))
                    .build();
        } finally {
            try {
                if (rs != null)
                    rs.close();
            } catch (SQLException e) {
            }
            try {
                if (stmt != null)
                    stmt.close();
            } catch (SQLException e) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException e) {
            }
        }
    }
}