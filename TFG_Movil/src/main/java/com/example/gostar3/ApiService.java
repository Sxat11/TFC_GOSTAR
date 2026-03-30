package com.example.gostar3;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {

    @POST("auth/login")
    Call<LoginResponse> login(@Body LoginRequest request);

    @POST("auth/registro")
    Call<LoginResponse> registro(@Body RegistroRequest request);

    @GET("auth/verificar")
    Call<VerificacionResponse> verificarToken(@Header("Authorization") String token);

    @POST("auth/logout")
    Call<MensajeResponse> logout(@Header("Authorization") String token);

    @GET("publicaciones")
    Call<List<Publicacion>> getPublicaciones(@Header("Authorization") String token,
                                             @Query("page") int page,
                                             @Query("limit") int limit);

    @GET("publicaciones/{id}")
    Call<Publicacion> getPublicacion(@Header("Authorization") String token, @Path("id") int id);

    @POST("publicaciones/{id}/like")
    Call<LikeResponse> darLike(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("publicaciones/{id}/like")
    Call<LikeResponse> quitarLike(@Header("Authorization") String token, @Path("id") int id);

    @DELETE("publicaciones/{id}")
    Call<Void> eliminarPublicacion(@Header("Authorization") String token, @Path("id") int id);

    @POST("publicaciones")
    Call<Publicacion> crearPublicacion(@Header("Authorization") String token, @Body Publicacion publicacion);

    @PUT("auth/perfil")
    Call<Usuario> actualizarPerfil(@Header("Authorization") String token, @Body Usuario usuario);

    @GET("auth/perfil")
    Call<Usuario> getPerfil(@Header("Authorization") String token);

    @GET("publicaciones/usuario/{usuarioId}")
    Call<List<Publicacion>> getPublicacionesByUsuario(@Header("Authorization") String token,
                                                      @Path("usuarioId") int usuarioId,
                                                      @Query("page") int page);

    @DELETE("auth/usuario")
    Call<Void> eliminarCuenta(@Header("Authorization") String token);
    class VerificacionResponse {
        private boolean valido;
        private Usuario usuario;
        public boolean isValido() { return valido; }
        public Usuario getUsuario() { return usuario; }
    }

    class MensajeResponse {
        private String mensaje;
        public String getMensaje() { return mensaje; }
    }
}