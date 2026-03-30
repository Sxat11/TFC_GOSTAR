package com.example.gostar3;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Muro extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_muro);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bottomNavigationView = findViewById(R.id.bottom_navigation);

        // Cargar fragment inicial
        cargarFragment(new InicioFragment(), false);

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navigation_inicio) {
                cargarFragment(new InicioFragment(), false);
                return true;
            } else if (itemId == R.id.navigation_buscar) {
                cargarFragment(new BuscarFragment(), false);
                return true;
            } else if (itemId == R.id.navigation_crear) {

                    startActivity(new Intent(Muro.this, CrearRecetaActivity.class));
                    return true;
            } else if (itemId == R.id.navigation_notificaciones) {
              //  cargarFragment(new NotificacionesFragment(), false);
                return true;
            } else if (itemId == R.id.navigation_perfil) {
                cargarFragment(new PerfilFragment(), false);
                return true;
            }

            return false;
        });
    }

    private void cargarFragment(Fragment fragment, boolean addToBackStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.container, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }

        transaction.commit();
    }
}