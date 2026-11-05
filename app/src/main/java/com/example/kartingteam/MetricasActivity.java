package com.example.kartingteam;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;

import java.util.Locale;

public class MetricasActivity extends AppCompatActivity {

    // Datos de la última sesión (los mismos que usa la versión web)
    private static final float[] VUELTAS = {50.87f, 48.78f, 48.5f, 48.3f, 48.6f, 48.2f, 48.0f, 47.95f};
    private static final float[] TEMP_MOTOR = {76f, 80f, 84f, 86f, 87f, 88f, 88f, 89f};
    private static final String[] ETIQUETAS = {"V1", "V2", "V3", "V4", "V5", "V6", "V7", "V8"};

    private LinearLayout containerMetrics;
    private Button btnVueltas, btnTemperaturas;
    private float density;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_metricas);
        density = getResources().getDisplayMetrics().density;

        Button btnVolver = findViewById(R.id.btnVolver);
        btnVueltas = findViewById(R.id.btnVueltas);
        btnTemperaturas = findViewById(R.id.btnTemperaturas);
        containerMetrics = findViewById(R.id.containerMetrics);

        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Pestaña inicial
        seleccionarTab(true);
        mostrarVueltas();

        btnVueltas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTab(true);
                mostrarVueltas();
            }
        });

        btnTemperaturas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarTab(false);
                mostrarTemperaturas();
            }
        });
    }

    // Pinta de naranja la pestaña activa y deja la otra apagada
    private void seleccionarTab(boolean vueltas) {
        btnVueltas.setBackgroundResource(vueltas ? R.drawable.bg_button_red : R.drawable.bg_pill_button);
        btnVueltas.setTextColor(ContextCompat.getColor(this, vueltas ? R.color.text_white : R.color.text_gray));
        btnTemperaturas.setBackgroundResource(vueltas ? R.drawable.bg_pill_button : R.drawable.bg_button_red);
        btnTemperaturas.setTextColor(ContextCompat.getColor(this, vueltas ? R.color.text_gray : R.color.text_white));
    }

    private void mostrarVueltas() {
        containerMetrics.removeAllViews();

        LinearLayout fila = crearFila();
        fila.addView(crearTarjetaStat(getString(R.string.metricas_promedio),
                String.format(Locale.US, "%.2f", promedio(VUELTAS)), R.color.text_white, 8));
        fila.addView(crearTarjetaStat(getString(R.string.metricas_mejor),
                String.format(Locale.US, "%.2f", minimo(VUELTAS)), R.color.racing_red, 8));
        fila.addView(crearTarjetaStat(getString(R.string.label_consistencia),
                getString(R.string.val_consistencia), R.color.accent_blue, 0));
        containerMetrics.addView(fila);

        containerMetrics.addView(crearTarjetaGrafico(
                getString(R.string.metricas_chart_vueltas), VUELTAS, "%.2f"));
    }

    private void mostrarTemperaturas() {
        containerMetrics.removeAllViews();

        LinearLayout fila = crearFila();
        fila.addView(crearTarjetaStat(getString(R.string.metricas_prom_motor),
                String.format(Locale.US, "%.1f°", promedio(TEMP_MOTOR)), R.color.text_white, 8));
        fila.addView(crearTarjetaStat(getString(R.string.metricas_min),
                String.format(Locale.US, "%.0f°", minimo(TEMP_MOTOR)), R.color.accent_blue, 8));
        fila.addView(crearTarjetaStat(getString(R.string.metricas_max),
                String.format(Locale.US, "%.0f°", maximo(TEMP_MOTOR)), R.color.racing_red, 0));
        containerMetrics.addView(fila);

        containerMetrics.addView(crearTarjetaGrafico(
                getString(R.string.metricas_chart_motor), TEMP_MOTOR, "%.0f°"));

        // Temperatura de neumáticos
        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(R.string.metricas_neumaticos);
        tvTitulo.setAllCaps(true);
        tvTitulo.setLetterSpacing(0.15f);
        tvTitulo.setTextSize(11f);
        tvTitulo.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
        tvTitulo.setPadding(0, dp(4), 0, dp(10));
        containerMetrics.addView(tvTitulo);

        LinearLayout fila1 = crearFila();
        fila1.addView(crearTarjetaStat(getString(R.string.tire_del_izq), "78 °C", R.color.accent_blue, 8));
        fila1.addView(crearTarjetaStat(getString(R.string.tire_del_der), "92 °C", R.color.racing_red, 0));
        containerMetrics.addView(fila1);

        LinearLayout fila2 = crearFila();
        fila2.addView(crearTarjetaStat(getString(R.string.tire_tras_izq), "74 °C", R.color.accent_blue, 8));
        fila2.addView(crearTarjetaStat(getString(R.string.tire_tras_der), "81 °C", R.color.accent_blue, 0));
        containerMetrics.addView(fila2);
    }

    // Fila horizontal para acomodar tarjetas una al lado de la otra
    private LinearLayout crearFila() {
        LinearLayout fila = new LinearLayout(this);
        fila.setOrientation(LinearLayout.HORIZONTAL);
        fila.setBaselineAligned(false);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, dp(10));
        fila.setLayoutParams(params);
        return fila;
    }

    // Tarjeta con número grande y etiqueta en mayúsculas
    private View crearTarjetaStat(String label, String valor, int colorRes, int margenFinalDp) {
        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setBackgroundResource(R.drawable.bg_card);
        tarjeta.setPadding(dp(14), dp(14), dp(14), dp(14));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
        params.setMarginEnd(dp(margenFinalDp));
        tarjeta.setLayoutParams(params);

        TextView tvValor = new TextView(this);
        tvValor.setText(valor);
        tvValor.setTextColor(ContextCompat.getColor(this, colorRes));
        tvValor.setTextSize(26f);
        tvValor.setIncludeFontPadding(false);
        Typeface condensada = ResourcesCompat.getFont(this, R.font.barlow_condensed_extrabold_italic);
        if (condensada != null) {
            tvValor.setTypeface(condensada);
        }

        TextView tvLabel = new TextView(this);
        tvLabel.setText(label);
        tvLabel.setAllCaps(true);
        tvLabel.setLetterSpacing(0.15f);
        tvLabel.setTextSize(10f);
        tvLabel.setTextColor(ContextCompat.getColor(this, R.color.text_gray));
        tvLabel.setPadding(0, dp(4), 0, 0);
        Typeface media = ResourcesCompat.getFont(this, R.font.barlow_medium);
        if (media != null) {
            tvLabel.setTypeface(media);
        }

        tarjeta.addView(tvValor);
        tarjeta.addView(tvLabel);
        return tarjeta;
    }

    // Tarjeta ancha con título y gráfico de línea
    private View crearTarjetaGrafico(String titulo, float[] valores, String formato) {
        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setBackgroundResource(R.drawable.bg_card);
        tarjeta.setPadding(dp(16), dp(16), dp(16), dp(12));

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0, 0, 0, dp(12));
        tarjeta.setLayoutParams(params);

        TextView tvTitulo = new TextView(this);
        tvTitulo.setText(titulo);
        tvTitulo.setAllCaps(true);
        tvTitulo.setLetterSpacing(0.05f);
        tvTitulo.setTextSize(16f);
        tvTitulo.setTextColor(ContextCompat.getColor(this, R.color.text_white));
        Typeface negrita = ResourcesCompat.getFont(this, R.font.barlow_condensed_bold);
        if (negrita != null) {
            tvTitulo.setTypeface(negrita);
        }

        LineChartView grafico = new LineChartView(this);
        grafico.setDatos(valores, ETIQUETAS, formato, ContextCompat.getColor(this, R.color.racing_red));
        LinearLayout.LayoutParams paramsGrafico = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, dp(210));
        paramsGrafico.setMargins(0, dp(12), 0, 0);
        grafico.setLayoutParams(paramsGrafico);

        tarjeta.addView(tvTitulo);
        tarjeta.addView(grafico);
        return tarjeta;
    }

    private float promedio(float[] v) {
        float suma = 0;
        for (float x : v) suma += x;
        return suma / v.length;
    }

    private float minimo(float[] v) {
        float m = v[0];
        for (float x : v) m = Math.min(m, x);
        return m;
    }

    private float maximo(float[] v) {
        float m = v[0];
        for (float x : v) m = Math.max(m, x);
        return m;
    }

    private int dp(int valor) {
        return (int) (valor * density);
    }
}