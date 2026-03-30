package com.example.gostar3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrearCuenta extends AppCompatActivity {

    private EditText editTextNombre, editTextApellido, editTextPais, editTextRegion;
    private EditText editTextCorreo, editTextPassword, editTextPassword2;
    private Button btnCrearCuenta;
    private TextView loginLink;

    private ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear_cuenta);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Inicializar vistas
        editTextNombre = findViewById(R.id.editTextNombre2);
        editTextApellido = findViewById(R.id.editTextApellido2);
        editTextPais = findViewById(R.id.editTextPais);
        editTextRegion = findViewById(R.id.editTextRegion);
        editTextCorreo = findViewById(R.id.editTextCorreo);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        editTextPassword2 = findViewById(R.id.editTextTextPassword2);
        btnCrearCuenta = findViewById(R.id.btnCrearCuenta);

        // Buscar el TextView para volver al login (si existe en tu XML)
        // Si no tienes este TextView, comenta estas líneas
//        loginLink = findViewById(R.id.loginLink);
//        if (loginLink != null) {
//            loginLink.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    finish();
//                }
//            });
//        }

        apiService = ApiClient.getClient();

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registrarUsuario();
            }
        });
    }

    private void registrarUsuario() {
        String nombre = editTextNombre.getText().toString().trim();
        String apellido = editTextApellido.getText().toString().trim();
        String pais = editTextPais.getText().toString().trim();
        String region = editTextRegion.getText().toString().trim();
        String email = editTextCorreo.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String password2 = editTextPassword2.getText().toString().trim();

        // Validaciones
        if (nombre.isEmpty() || apellido.isEmpty() || pais.isEmpty() ||
                email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Completa todos los campos obligatorios",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(password2)) {
            Toast.makeText(this, "Las contraseñas no coinciden",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        if (password.length() < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        // Deshabilitar botón
        btnCrearCuenta.setEnabled(false);
        btnCrearCuenta.setText("Registrando...");

        // Crear username a partir del email
        String username = email.split("@")[0] + (int)(Math.random() * 1000);
        String nombreCompleto = nombre + " " + apellido;
        String bio = "País: " + pais;
        if (!region.isEmpty()) {
            bio += ", Región: " + region;
        }

        RegistroRequest request = new RegistroRequest(
                username, email, password, nombreCompleto, bio
        );

        apiService.registro(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                btnCrearCuenta.setEnabled(true);
                btnCrearCuenta.setText("Crear cuenta");

                if (response.isSuccessful() && response.body() != null) {
                    Toast.makeText(CrearCuenta.this,
                            "¡Registro exitoso! Ya puedes iniciar sesión",
                            Toast.LENGTH_LONG).show();

                    // Volver al login
                    finish();

                } else {
                    String errorMsg = "Error en el registro";
                    try {
                        if (response.code() == 409) {
                            errorMsg = "El email o usuario ya está registrado";
                        } else if (response.errorBody() != null) {
                            errorMsg = response.errorBody().string();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(CrearCuenta.this, errorMsg, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                btnCrearCuenta.setEnabled(true);
                btnCrearCuenta.setText("Crear cuenta");
                Toast.makeText(CrearCuenta.this,
                        "Error de conexión: " + t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }
}