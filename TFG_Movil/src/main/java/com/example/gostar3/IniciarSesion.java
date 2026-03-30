package com.example.gostar3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gostar3.R;
import com.example.gostar3.LoginResponse;
import com.example.gostar3.Muro;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IniciarSesion extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private CheckBox recordarCheckbox;
    private Button btnEntrar;
    private TextView crearCuentaTxt;

    private ApiService apiService;
    private SharedPreferences sharedPreferences;
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_iniciar_sesion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar Gson
        gson = new Gson();

        // Inicializar vistas
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPassword = findViewById(R.id.editTextPassword);
        recordarCheckbox = findViewById(R.id.recordarcheckbox);
        btnEntrar = findViewById(R.id.btnEntrar);
        crearCuentaTxt = findViewById(R.id.CrearCuentatxt);

        // Inicializar API y SharedPreferences
        apiService = ApiClient.getClient();
        sharedPreferences = getSharedPreferences("GostarPrefs", MODE_PRIVATE);

        // Verificar si ya hay sesión guardada
        checkSavedSession();

        // Botón de login
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                iniciarSesion();
            }
        });

        // Link a registro
        crearCuentaTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(IniciarSesion.this, CrearCuenta.class);
                startActivity(intent);
            }
        });
    }

    private void checkSavedSession() {
        String token = sharedPreferences.getString("token", null);
        if (token != null && !token.isEmpty()) {
            // Verificar si el token sigue siendo válido
            apiService.verificarToken("Bearer " + token).enqueue(new Callback<ApiService.VerificacionResponse>() {
                @Override
                public void onResponse(Call<ApiService.VerificacionResponse> call,
                                       Response<ApiService.VerificacionResponse> response) {
                    if (response.isSuccessful() && response.body() != null &&
                            response.body().isValido()) {
                        // Token válido, ir a Principal
                        irAPrincipal();
                    } else {
                        // Token inválido, limpiar
                        sharedPreferences.edit().remove("token").apply();
                    }
                }

                @Override
                public void onFailure(Call<ApiService.VerificacionResponse> call, Throwable t) {
                    // Error de red, ignorar
                }
            });
        }
    }

    private void iniciarSesion() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Deshabilitar botón
        btnEntrar.setEnabled(false);
        btnEntrar.setText("Iniciando...");

        LoginRequest request = new LoginRequest(email, password);

        apiService.login(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnEntrar.setEnabled(true);
                btnEntrar.setText("Iniciar Sesión");

                if (response.isSuccessful() && response.body() != null) {
                    LoginResponse loginResponse = response.body();

                    // Guardar token y usuario
                    sharedPreferences.edit()
                            .putString("token", loginResponse.getToken())
                            .putString("usuario", gson.toJson(loginResponse.getUsuario()))
                            .apply();

                    Toast.makeText(IniciarSesion.this, "¡Bienvenido " +
                                    loginResponse.getUsuario().getNombre() + "!",
                            Toast.LENGTH_SHORT).show();

                    irAPrincipal();

                } else {
                    try {
                        String errorBody = response.errorBody().string();
                        Toast.makeText(IniciarSesion.this,
                                "Error: Usuario o contraseña incorrectos",
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(IniciarSesion.this,
                                "Error en el login",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnEntrar.setEnabled(true);
                btnEntrar.setText("Iniciar Sesión");
                Toast.makeText(IniciarSesion.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    private void irAPrincipal() {
        Intent intent = new Intent(IniciarSesion.this, Muro.class);
        startActivity(intent);
        finish();
    }
}