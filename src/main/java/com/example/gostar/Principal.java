package com.example.gostar;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Principal extends AppCompatActivity {
    ArrayList<Receta> recetas;
    RecyclerView rv;
    RecetaAdapter miAdaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);

        // 1. Configurar los insets (esto lo genera Android Studio por defecto)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 2. Inicializar la lista de datos (¡IMPORTANTE!)
        // Si no haces el 'new', recetas es null y la app se cerrará
        recetas = new ArrayList<>();
        cargarDatosDePrueba();

        // 3. Configurar el RecyclerView
        rv = findViewById(R.id.rv);

        // El LayoutManager es obligatorio para que los elementos se posicionen
        rv.setLayoutManager(new androidx.recyclerview.widget.LinearLayoutManager(this));

        // 4. Inicializar y asignar el adaptador
        miAdaptador = new RecetaAdapter(recetas, this);
        rv.setAdapter(miAdaptador);
    }

    // Método de ejemplo para rellenar la lista
    private void cargarDatosDePrueba() {
        recetas.add(new Receta("Tortilla de Patatas", "30 min",R.drawable.pizza));
        recetas.add(new Receta("Pasta Carbonara", "15 min",R.drawable.pimiento3));
        recetas.add(new Receta("Gazpacho", "10 min",R.drawable.food));
        recetas.add(new Receta("Gazpacho", "10 min",R.drawable.food));
        recetas.add(new Receta("Gazpacho", "10 min",R.drawable.food));

    }
}