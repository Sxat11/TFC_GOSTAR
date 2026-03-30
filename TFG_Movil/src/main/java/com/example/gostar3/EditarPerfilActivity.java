package com.example.gostar3;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditarPerfilActivity extends AppCompatActivity {

    private ImageView avatarImage;
    private EditText editNombre, editEmail, editBio;
    private TextView textUsername;
    private Button btnGuardar, btnCancelar, btnEliminarCuenta;
    private ScrollView scrollView;
    private View progressBar;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private Usuario usuarioActual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        inicializarVistas();
        configurarBotones();

        apiService = ApiClient.getClient();
        sharedPreferences = getSharedPreferences("GostarPrefs", MODE_PRIVATE);
        gson = new Gson();

        cargarDatosUsuario();
    }

    private void inicializarVistas() {
        avatarImage = findViewById(R.id.avatarImage);
        editNombre = findViewById(R.id.editNombre);
        editEmail = findViewById(R.id.editEmail);
        editBio = findViewById(R.id.editBio);
        textUsername = findViewById(R.id.textUsername);
        btnGuardar = findViewById(R.id.btnGuardar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminarCuenta = findViewById(R.id.btnEliminarCuenta);
        scrollView = findViewById(R.id.scrollView);
        progressBar = findViewById(R.id.progressBar);

        findViewById(R.id.btnBack).setOnClickListener(v -> finish());
    }

    private void configurarBotones() {
        btnGuardar.setOnClickListener(v -> guardarCambios());
        btnCancelar.setOnClickListener(v -> finish());
        btnEliminarCuenta.setOnClickListener(v -> confirmarEliminarCuenta());
    }

    private void cargarDatosUsuario() {
        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Primero intentamos cargar del SharedPreferences
        String usuarioJson = sharedPreferences.getString("usuario", null);
        if (usuarioJson != null) {
            usuarioActual = gson.fromJson(usuarioJson, Usuario.class);
            mostrarDatos();
        }

        // Luego cargamos del servidor para datos actualizados
        apiService.getPerfil("Bearer " + token).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mostrarCargando(false);

                if (response.isSuccessful() && response.body() != null) {
                    usuarioActual = response.body();
                    mostrarDatos();

                    // Actualizar SharedPreferences
                    String usuarioJson = gson.toJson(usuarioActual);
                    sharedPreferences.edit()
                            .putString("usuario", usuarioJson)
                            .apply();

                } else {
                    Toast.makeText(EditarPerfilActivity.this,
                            "Error al cargar perfil", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(EditarPerfilActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void mostrarDatos() {
        if (usuarioActual != null) {
            editNombre.setText(usuarioActual.getNombre() != null ? usuarioActual.getNombre() : "");
            editEmail.setText(usuarioActual.getEmail() != null ? usuarioActual.getEmail() : "");
            editBio.setText(usuarioActual.getBio() != null ? usuarioActual.getBio() : "");
            textUsername.setText("@" + usuarioActual.getUsername());
        }
    }

    private void guardarCambios() {
        String nombre = editNombre.getText().toString().trim();
        String email = editEmail.getText().toString().trim();
        String bio = editBio.getText().toString().trim();

        if (nombre.isEmpty()) {
            editNombre.setError("El nombre no puede estar vacío");
            return;
        }

        if (email.isEmpty()) {
            editEmail.setError("El email no puede estar vacío");
            return;
        }

        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Crear objeto con los datos actualizados
        Usuario usuarioActualizado = new Usuario();
        usuarioActualizado.setNombre(nombre);
        usuarioActualizado.setEmail(email);
        usuarioActualizado.setBio(bio);

        apiService.actualizarPerfil("Bearer " + token, usuarioActualizado).enqueue(new Callback<Usuario>() {
            @Override
            public void onResponse(Call<Usuario> call, Response<Usuario> response) {
                mostrarCargando(false);

                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuarioNuevo = response.body();

                    // Actualizar SharedPreferences
                    String usuarioJson = gson.toJson(usuarioNuevo);
                    sharedPreferences.edit()
                            .putString("usuario", usuarioJson)
                            .apply();

                    Toast.makeText(EditarPerfilActivity.this,
                            "Perfil actualizado correctamente", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    String errorMsg = "Error al actualizar el perfil";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EditarPerfilActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Usuario> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(EditarPerfilActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void confirmarEliminarCuenta() {
        new AlertDialog.Builder(this)
                .setTitle("Eliminar cuenta")
                .setMessage("¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer y perderás todas tus recetas y likes.")
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        eliminarCuenta();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminarCuenta() {
        mostrarCargando(true);

        String token = sharedPreferences.getString("token", null);
        if (token == null) {
            Toast.makeText(this, "Sesión no válida", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        apiService.eliminarCuenta("Bearer " + token).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                mostrarCargando(false);

                if (response.isSuccessful()) {
                    // Limpiar SharedPreferences
                    sharedPreferences.edit().clear().apply();

                    Toast.makeText(EditarPerfilActivity.this,
                            "Cuenta eliminada correctamente", Toast.LENGTH_LONG).show();

                    // Redirigir al login
                    Intent intent = new Intent(EditarPerfilActivity.this, IniciarSesion.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                } else {
                    String errorMsg = "Error al eliminar la cuenta";
                    try {
                        if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(EditarPerfilActivity.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                mostrarCargando(false);
                Toast.makeText(EditarPerfilActivity.this,
                        "Error de conexión: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void mostrarCargando(boolean mostrar) {
        progressBar.setVisibility(mostrar ? View.VISIBLE : View.GONE);
        scrollView.setVisibility(mostrar ? View.GONE : View.VISIBLE);
        btnGuardar.setEnabled(!mostrar);
        btnCancelar.setEnabled(!mostrar);
        btnEliminarCuenta.setEnabled(!mostrar);
    }
}