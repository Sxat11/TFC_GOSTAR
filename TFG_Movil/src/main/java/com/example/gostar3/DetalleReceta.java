package com.example.gostar3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetalleReceta extends AppCompatActivity {

    private ImageView imagenPrincipal, likeButton;
    private TextView titulo, duracion, dificultad, calorias, descripcion, usuarioNombre, likesCount;
    private LinearLayout ingredientesContainer, pasosContainer, imagenesContainer;
    private Button btnEliminar, btnEditar;
    private NestedScrollView scrollView;
    private View progressBar;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private int recetaId;
    private Publicacion receta;
    private boolean isAuthor = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_receta);

        // Obtener ID de la receta del intent
        recetaId = getIntent().getIntExtra("receta_id", -1);
        if (recetaId == -1) {
            Toast.makeText(this, "Error al cargar la receta", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        inicializarVistas();
        configurarBotones();

        apiService = ApiClient.getClient();
        sharedPreferences = getSharedPreferences("GostarPrefs", MODE_PRIVATE);

        cargarReceta();
    }

    private void inicializarVistas() {
        imagenPrincipal = findViewById(R.id.imagenPrincipal);
        titulo = findViewById(R.id.tituloReceta);
        duracion = findViewById(R.id.duracionReceta);
        dificultad = findViewById(R.id.dificultadReceta);
        calorias = findViewById(R.id.caloriasReceta);
        descripcion = findViewById(R.id.descripcionReceta);
        usuarioNombre = findViewById(R.id.usuarioNombre);
        likesCount = findViewById(R.id.likesCount);
        likeButton = findViewById(R.id.likeButton);
        btnEliminar = findViewById(R.id.btnEliminar);
        btnEditar = findViewById(R.id.btnEditar);
        ingredientesContainer = findViewById(R.id.ingredientesContainer);
        pasosContainer = findViewById(R.id.pasosContainer);
        imagenesContainer = findViewById(R.id.imagenesContainer);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);
    }

    private void configurarBotones() {
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        likeButton.setOnClickListener(v -> toggleLike());

        btnEditar.setOnClickListener(v -> {
            // TODO: Implementar edición
            Toast.makeText(this, "Editar receta - Próximamente", Toast.LENGTH_SHORT).show();
        });

        btnEliminar.setOnClickListener(v -> eliminarReceta());
    }

    private void cargarReceta() {
        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService.getPublicacion("Bearer " + token, recetaId).enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                mostrarCargando(false);

                if (response.isSuccessful() && response.body() != null) {
                    receta = response.body();
                    mostrarReceta();
                } else {
                    Toast.makeText(DetalleReceta.this, "Error al cargar la receta", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(DetalleReceta.this, "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void mostrarReceta() {
        // Verificar si el usuario actual es el autor
        String usuarioJson = sharedPreferences.getString("usuario", null);
        if (usuarioJson != null) {
            Usuario usuarioActual = new Gson().fromJson(usuarioJson, Usuario.class);
            isAuthor = usuarioActual.getId() == receta.getUsuarioId();
            btnEditar.setVisibility(isAuthor ? View.VISIBLE : View.GONE);
            btnEliminar.setVisibility(isAuthor ? View.VISIBLE : View.GONE);
        }

        // Datos básicos
        titulo.setText(receta.getTitulo());
        duracion.setText("⏱️ " + receta.getDuracion());
        usuarioNombre.setText(receta.getUsuarioNombre());
        likesCount.setText(String.valueOf(receta.getLikes()));

        // Dificultad y calorías
        dificultad.setText("🔥 " + (receta.getDificultad() != null ? receta.getDificultad() : "No especificada"));
        calorias.setText("⚡ " + receta.getCalorias() + " kcal");

        // Descripción
        if (receta.getDescripcion() != null && !receta.getDescripcion().isEmpty()) {
            descripcion.setText(receta.getDescripcion());
        } else {
            descripcion.setText("Sin descripción");
        }

        // Imagen principal
        if (receta.getImagenPrincipal() != null && !receta.getImagenPrincipal().isEmpty()) {
            Picasso.get().load(receta.getImagenPrincipal()).into(imagenPrincipal);
        }

        // Color del like
        actualizarColorLike();

        // Ingredientes
        ingredientesContainer.removeAllViews();
        if (receta.getIngredientes() != null) {
            for (String ingrediente : receta.getIngredientes()) {
                View item = getLayoutInflater().inflate(R.layout.item_ingrediente, ingredientesContainer, false);
                TextView textView = item.findViewById(R.id.textIngrediente);
                textView.setText("• " + ingrediente);
                ingredientesContainer.addView(item);
            }
        }

        // Pasos
        pasosContainer.removeAllViews();
        if (receta.getPasos() != null) {
            for (int i = 0; i < receta.getPasos().size(); i++) {
                View item = getLayoutInflater().inflate(R.layout.item_paso, pasosContainer, false);
                TextView textView = item.findViewById(R.id.textPaso);
                textView.setText((i + 1) + ". " + receta.getPasos().get(i));
                pasosContainer.addView(item);
            }
        }

        // Imágenes adicionales
        imagenesContainer.removeAllViews();
        if (receta.getImagenesAdicionales() != null && !receta.getImagenesAdicionales().isEmpty()) {
            for (String url : receta.getImagenesAdicionales()) {
                View item = getLayoutInflater().inflate(R.layout.item_imagen_adicional, imagenesContainer, false);
                ImageView imageView = item.findViewById(R.id.imagenAdicional);
                Picasso.get().load(url).into(imageView);

                imageView.setOnClickListener(v -> {
                    // Abrir imagen en grande (podríamos implementar un zoom)
                    Toast.makeText(this, "Ver imagen", Toast.LENGTH_SHORT).show();
                });

                imagenesContainer.addView(item);
            }
        }
    }

    private void toggleLike() {
        String token = sharedPreferences.getString("token", null);
        if (token == null) return;

        Call<LikeResponse> call;
        if (receta.isLikedByCurrentUser()) {
            call = apiService.quitarLike("Bearer " + token, receta.getId());
        } else {
            call = apiService.darLike("Bearer " + token, receta.getId());
        }

        call.enqueue(new Callback<LikeResponse>() {
            @Override
            public void onResponse(Call<LikeResponse> call, Response<LikeResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    receta.setLikes(response.body().getLikes());
                    receta.setLikedByCurrentUser(response.body().isLiked());
                    likesCount.setText(String.valueOf(receta.getLikes()));
                    actualizarColorLike();
                }
            }

            @Override
            public void onFailure(Call<LikeResponse> call, Throwable t) {
                Toast.makeText(DetalleReceta.this, "Error al dar like", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void actualizarColorLike() {
        if (receta.isLikedByCurrentUser()) {
            likeButton.setColorFilter(getColor(android.R.color.holo_red_dark));
        } else {
            likeButton.setColorFilter(getColor(android.R.color.darker_gray));
        }
    }

    private void eliminarReceta() {
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Eliminar receta")
                .setMessage("¿Estás seguro de que quieres eliminar esta receta?")
                .setPositiveButton("Eliminar", (dialog, which) -> {
                    String token = sharedPreferences.getString("token", null);
                    if (token == null) return;

                    apiService.eliminarPublicacion("Bearer " + token, recetaId).enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(DetalleReceta.this, "Receta eliminada", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(DetalleReceta.this, "Error al eliminar", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(DetalleReceta.this, "Error de conexión", Toast.LENGTH_SHORT).show();
                        }
                    });
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void mostrarCargando(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(mostrar ? View.GONE : View.VISIBLE);
    }
}