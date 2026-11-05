package com.example.kartingteam;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

        // 4. Evento Click para la pantalla de Setup del Kart (pasa el nombre del piloto)
        Button btnSetup = findViewById(R.id.btnSetup);
        btnSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SetupActivity.class);
                intent.putExtra("nombrePiloto", getString(R.string.driver_name));
                startActivity(intent);
            }
        });
    }

    private void generarBarrasProgresoDinamicas(LinearLayout container) {
        int totalCarreras = 6;
        int carrerasCompletadas = 4;
        float density = getResources().getDisplayMetrics().density;
        int margen = (int) (3 * density);

        for (int i = 0; i < totalCarreras; i++) {
            View segment = new View(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1.0f
            );
            params.setMargins(margen, 0, margen, 0);
            segment.setLayoutParams(params);

            // Segmento redondeado: rojo si la carrera ya se corrió, gris si no
            GradientDrawable forma = new GradientDrawable();
            forma.setCornerRadius(3 * density);
            forma.setColor(ContextCompat.getColor(this,
                    i < carrerasCompletadas ? R.color.racing_red : R.color.surface_card_alt));
            segment.setBackground(forma);

            container.addView(segment);
        }
    }
}