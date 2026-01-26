package com.example.gostar;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Crear extends AppCompatActivity {

    TextView miLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_crear);

        // Ajuste de Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Aquí ya fuera del listener
//        miLink = findViewById(R.id.CrearCuentatxt);
//
//        miLink.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Intent correcto
//                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
//                startActivity(intent);
//            }
//        });
    }
}
