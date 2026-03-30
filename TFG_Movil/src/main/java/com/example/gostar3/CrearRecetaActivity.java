package com.example.gostar3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearRecetaActivity extends AppCompatActivity {

    private EditText editTitulo, editDuracion, editImagenPrincipal, editDescripcion, editCalorias;
    private TextView selectDificultad;
    private LinearLayout ingredientesContainer, pasosContainer, imagenesContainer;
    private Button btnAgregarIngrediente, btnAgregarPaso, btnAgregarImagen, btnPublicar;
    private ScrollView scrollView;
    private View progressBar;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private List<EditText> ingredienteInputs = new ArrayList<>();
    private List<EditText> pasoInputs = new ArrayList<>();
    private List<EditText> imagenInputs = new ArrayList<>();
    private String dificultadSeleccionada = "Media";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_receta);

        inicializarVistas();
        configurarBotones();

        apiService = ApiClient.getClient();
        sharedPreferences = getSharedPreferences("GostarPrefs", MODE_PRIVATE);

        // Agregar primer ingrediente y paso por defecto
        agregarIngrediente();
        agregarPaso();
    }

    private void inicializarVistas() {
        editTitulo = findViewById(R.id.editTitulo);
        editDuracion = findViewById(R.id.editDuracion);
        editImagenPrincipal = findViewById(R.id.editImagenPrincipal);
        editDescripcion = findViewById(R.id.editDescripcion);
        editCalorias = findViewById(R.id.editCalorias);
        selectDificultad = findViewById(R.id.selectDificultad);

        ingredientesContainer = findViewById(R.id.ingredientesContainer);
        pasosContainer = findViewById(R.id.pasosContainer);
        imagenesContainer = findViewById(R.id.imagenesContainer);

        btnAgregarIngrediente = findViewById(R.id.btnAgregarIngrediente);
        btnAgregarPaso = findViewById(R.id.btnAgregarPaso);
        btnAgregarImagen = findViewById(R.id.btnAgregarImagen);
        btnPublicar = findViewById(R.id.btnPublicar);

        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void configurarBotones() {
        // Selector de dificultad
        selectDificultad.setOnClickListener(v -> {
            String[] dificultades = {"Fácil", "Media", "Difícil"};
            new androidx.appcompat.app.AlertDialog.Builder(this)
                    .setTitle("Selecciona dificultad")
                    .setItems(dificultades, (dialog, which) -> {
                        dificultadSeleccionada = dificultades[which];
                        selectDificultad.setText(dificultadSeleccionada);
                    })
                    .show();
        });

        btnAgregarIngrediente.setOnClickListener(v -> agregarIngrediente());
        btnAgregarPaso.setOnClickListener(v -> agregarPaso());
        btnAgregarImagen.setOnClickListener(v -> agregarImagen());
        btnPublicar.setOnClickListener(v -> publicarReceta());
    }

    private void agregarIngrediente() {
        View item = getLayoutInflater().inflate(R.layout.item_crear_ingrediente, ingredientesContainer, false);
        EditText input = item.findViewById(R.id.inputIngrediente);
        Button btnEliminar = item.findViewById(R.id.btnEliminar);

        ingredienteInputs.add(input);

        btnEliminar.setOnClickListener(v -> {
            ingredientesContainer.removeView(item);
            ingredienteInputs.remove(input);
        });

        ingredientesContainer.addView(item);
    }

    private void agregarPaso() {
        View item = getLayoutInflater().inflate(R.layout.item_crear_caso, pasosContainer, false);
        EditText input = item.findViewById(R.id.inputPaso);
        Button btnEliminar = item.findViewById(R.id.btnEliminar);

        pasoInputs.add(input);

        btnEliminar.setOnClickListener(v -> {
            pasosContainer.removeView(item);
            pasoInputs.remove(input);
        });

        pasosContainer.addView(item);
    }

    private void agregarImagen() {
        View item = getLayoutInflater().inflate(R.layout.item_crear_imagen, imagenesContainer, false);
        EditText input = item.findViewById(R.id.inputImagen);
        Button btnEliminar = item.findViewById(R.id.btnEliminar);

        imagenInputs.add(input);

        btnEliminar.setOnClickListener(v -> {
            imagenesContainer.removeView(item);
            imagenInputs.remove(input);
        });

        imagenesContainer.addView(item);
    }

    private void publicarReceta() {
        // Validaciones
        String titulo = editTitulo.getText().toString().trim();
        String duracion = editDuracion.getText().toString().trim();
        String imagenPrincipal = editImagenPrincipal.getText().toString().trim();

        if (titulo.isEmpty()) {
            editTitulo.setError("El título es obligatorio");
            return;
        }

        if (duracion.isEmpty()) {
            editDuracion.setError("La duración es obligatoria");
            return;
        }

        if (imagenPrincipal.isEmpty()) {
            editImagenPrincipal.setError("La imagen principal es obligatoria");
            return;
        }

        // Recoger ingredientes
        List<String> ingredientes = new ArrayList<>();
        for (EditText input : ingredienteInputs) {
            String texto = input.getText().toString().trim();
            if (!texto.isEmpty()) {
                ingredientes.add(texto);
            }
        }

        if (ingredientes.isEmpty()) {
            Toast.makeText(this, "Añade al menos un ingrediente", Toast.LENGTH_SHORT).show();
            return;
        }

        // Recoger pasos
        List<String> pasos = new ArrayList<>();
        for (EditText input : pasoInputs) {
            String texto = input.getText().toString().trim();
            if (!texto.isEmpty()) {
                pasos.add(texto);
            }
        }

        if (pasos.isEmpty()) {
            Toast.makeText(this, "Añade al menos un paso", Toast.LENGTH_SHORT).show();
            return;
        }

        // Recoger imágenes adicionales
        List<String> imagenesAdicionales = new ArrayList<>();
        for (EditText input : imagenInputs) {
            String texto = input.getText().toString().trim();
            if (!texto.isEmpty()) {
                imagenesAdicionales.add(texto);
            }
        }

        // Crear objeto Publicacion
        Publicacion nuevaPublicacion = new Publicacion();
        nuevaPublicacion.setTitulo(titulo);
        nuevaPublicacion.setDuracion(duracion);
        nuevaPublicacion.setImagenPrincipal(imagenPrincipal);
        nuevaPublicacion.setDescripcion(editDescripcion.getText().toString().trim());
        nuevaPublicacion.setDificultad(dificultadSeleccionada);

        try {
            int calorias = Integer.parseInt(editCalorias.getText().toString().trim());
            nuevaPublicacion.setCalorias(calorias);
        } catch (NumberFormatException e) {
            nuevaPublicacion.setCalorias(0);
        }

        nuevaPublicacion.setIngredientes(ingredientes);
        nuevaPublicacion.setPasos(pasos);
        nuevaPublicacion.setImagenesAdicionales(imagenesAdicionales);

        // Enviar a la API
        enviarPublicacion(nuevaPublicacion);
    }

    private void enviarPublicacion(Publicacion publicacion) {
        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService.crearPublicacion("Bearer " + token, publicacion).enqueue(new Callback<Publicacion>() {
            @Override
            public void onResponse(Call<Publicacion> call, Response<Publicacion> response) {
                mostrarCargando(false);

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CrearRecetaActivity.this,
                            "¡Receta publicada con éxito!", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    String errorMsg = "Error al publicar";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CrearRecetaActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Publicacion> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(CrearRecetaActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarCargando(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(mostrar ? View.GONE : View.VISIBLE);
        btnPublicar.setEnabled(!mostrar);
    }
}