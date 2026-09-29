package com.example.kartingteam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 1. Inserción dinámica por código (Barra de progreso)
        LinearLayout progressContainer = findViewById(R.id.containerProgressBar);
        generarBarrasProgresoDinamicas(progressContainer);

        // 2. Evento Click para la pantalla de Métricas
        Button btnVerMetricas = findViewById(R.id.btnVerMetricas);
        btnVerMetricas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MetricasActivity.class);
                startActivity(intent);
            }
        });

        // 3. Evento Click para la pantalla de Experiencia / Sensaciones
        Button btnExperiencia = findViewById(R.id.btnExperiencia);
        btnExperiencia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExperienciaActivity.class);
                startActivity(intent);
            }
        });
    }

    private void generarBarrasProgresoDinamicas(LinearLayout container) {
        int totalCarreras = 6;
        int carrerasCompletadas = 4;

        for (int i = 0; i < totalCarreras; i++) {
            View segment = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            params.setMargins(4, 0, 4, 0);
            segment.setLayoutParams(params);

            if (i < carrerasCompletadas) {
                segment.setBackgroundColor(Color.parseColor("#D32F2F")); // Rojo activo
            } else {
                segment.setBackgroundColor(Color.parseColor("#2A2D36")); // Apagado
            }
            container.addView(segment);
        }
    }
}