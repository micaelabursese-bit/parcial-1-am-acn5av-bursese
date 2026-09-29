package com.example.kartingteam;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class MetricasActivity extends AppCompatActivity {

    private LinearLayout containerMetrics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metricas);

        Button btnVolver = findViewById(R.id.btnVolver);
        final Button btnVueltas = findViewById(R.id.btnVueltas);
        final Button btnTemperaturas = findViewById(R.id.btnTemperaturas);
        containerMetrics = findViewById(R.id.containerMetrics);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mostrarVueltas();

        btnVueltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnVueltas.setTextColor(Color.WHITE);
                btnTemperaturas.setTextColor(Color.parseColor("#808080"));
                mostrarVueltas();
            }
        });

        btnTemperaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnTemperaturas.setTextColor(Color.WHITE);
                btnVueltas.setTextColor(Color.parseColor("#808080"));
                mostrarTemperaturas();
            }
        });
    }

    private void mostrarVueltas() {
        containerMetrics.removeAllViews();

        // 1. Tarjeta Ancha (Tiempo por vuelta)
        containerMetrics.addView(crearTarjeta("Tiempo por vuelta", "48.65 PROM.", Color.WHITE, true));

        // 2. Fila Doble (Consistencia + Mejor vuelta)
        LinearLayout row = crearFila();
        row.addView(crearTarjeta("Consistencia", "94%", Color.parseColor("#4285F4"), false));
        row.addView(crearTarjeta("Mejor vuelta", "47.95", Color.parseColor("#E53935"), false));
        containerMetrics.addView(row);
    }

    private void mostrarTemperaturas() {
        containerMetrics.removeAllViews();

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText("TEMPERATURA DE NEUMÁTICOS");
        tvTitulo.setTextColor(Color.parseColor("#808080"));
        tvTitulo.setTextSize(11f);
        tvTitulo.setPadding(0, 0, 0, 16);
        containerMetrics.addView(tvTitulo);

        // Fila 1: Delanteros
        LinearLayout row1 = crearFila();
        row1.addView(crearTarjeta("Delantero izq.", "78 °C", Color.parseColor("#4285F4"), false));
        row1.addView(crearTarjeta("Delantero der.", "92 °C", Color.parseColor("#E53935"), false));
        containerMetrics.addView(row1);

        // Fila 2: Traseros
        LinearLayout row2 = crearFila();
        row2.addView(crearTarjeta("Trasero izq.", "74 °C", Color.parseColor("#4285F4"), false));
        row2.addView(crearTarjeta("Trasero der.", "81 °C", Color.parseColor("#4285F4"), false));
        containerMetrics.addView(row2);
    }

    private LinearLayout crearFila() {
        LinearLayout row = new LinearLayout(this);
        row.setOrientation(LinearLayout.HORIZONTAL);
        row.setWeightSum(2);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, 12);
        row.setLayoutParams(params);
        return row;
    }

    private View crearTarjeta(String label, String value, int valueColor, boolean isFullWidth) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setBackgroundResource(R.drawable.bg_card);
        card.setPadding(24, 24, 24, 24);

        LinearLayout.LayoutParams params;
        if (isFullWidth) {
            params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(0, 0, 0, 12);
        } else {
            params = new LinearLayout.LayoutParams(
                    0,
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    1.0f
            );
            params.setMargins(6, 0, 6, 0);
        }
        card.setLayoutParams(params);

        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setTextColor(Color.parseColor("#808080"));
        tvLabel.setTextSize(11f);

        TextView tvValue = new TextView(this);
        tvValue.setText(value);
        tvValue.setTextColor(valueColor);
        tvValue.setTextSize(18f);
        tvValue.setTypeface(null, android.graphics.Typeface.BOLD);

        card.addView(tvLabel);
        card.addView(tvValue);

        return card;
    }
}