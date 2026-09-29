package com.example.kartingteam;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Calendar;

public class ExperienciaActivity extends AppCompatActivity {

    private Button btnFecha;
    private EditText etComentarios;
    private RadioGroup rgTipoSesion;
    private LinearLayout contenedorExperiencias;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_experiencia);

        // Binding de vistas
        Button btnVolver = findViewById(R.id.btnVolverExp);
        btnFecha = findViewById(R.id.btnFecha);
        etComentarios = findViewById(R.id.etComentarios);
        rgTipoSesion = findViewById(R.id.rgTipoSesion);
        contenedorExperiencias = findViewById(R.id.contenedorExperiencias);
        Button btnGuardar = findViewById(R.id.btnGuardarExp);

        // Listener: Volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener: DatePicker
        btnFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarDatePicker();
            }
        });

        // Listener: Guardar y Crear Elemento Dinámico
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarExperienciaDinamica();
            }
        });
    }

    private void mostrarDatePicker() {
        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                ExperienciaActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int y, int m, int d) {
                        btnFecha.setText(d + "/" + (m + 1) + "/" + y);
                    }
                },
                year, month, day
        );
        dialog.show();
    }

    private void guardarExperienciaDinamica() {
        String comentarios = etComentarios.getText().toString().trim();
        String fecha = btnFecha.getText().toString();

        if (TextUtils.isEmpty(comentarios)) {
            Toast.makeText(this, "Por favor ingrese un comentario", Toast.LENGTH_SHORT).show();
            return;
        }

        // CORRECCIÓN CRÍTICA: Validar si hay un RadioButton seleccionado
        int selectedId = rgTipoSesion.getCheckedRadioButtonId();
        String tipoSesion = "Sesión"; // Valor por defecto si no se selecciona nada

        if (selectedId != -1) {
            RadioButton rbSeleccionado = findViewById(selectedId);
            if (rbSeleccionado != null) {
                tipoSesion = rbSeleccionado.getText().toString();
            }
        }

        // CREACIÓN DINÁMICA DE VISTAS

        // 1. Crear un LinearLayout vertical para la tarjeta
        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setBackgroundResource(R.drawable.bg_card);
        tarjeta.setPadding(32, 24, 32, 24);

        LinearLayout.LayoutParams paramsTarjeta = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        paramsTarjeta.setMargins(0, 0, 0, 24);
        tarjeta.setLayoutParams(paramsTarjeta);

        // 2. Encabezado de la tarjeta (Fecha y Tipo de Sesión)
        TextView tvHeader = new TextView(this);
        String textoHeader = "🏎️ " + tipoSesion + " • " + (fecha.equals("Seleccionar fecha") ? "Hoy" : fecha);
        tvHeader.setText(textoHeader);
        tvHeader.setTextColor(Color.parseColor("#FF3B30"));
        tvHeader.setTextSize(13);
        tvHeader.setTypeface(null, android.graphics.Typeface.BOLD);

        // 3. Contenido de los comentarios
        TextView tvBody = new TextView(this);
        tvBody.setText(comentarios);
        tvBody.setTextColor(Color.WHITE);
        tvBody.setTextSize(14);
        tvBody.setPadding(0, 8, 0, 0);

        // 4. Armar la tarjeta
        tarjeta.addView(tvHeader);
        tarjeta.addView(tvBody);

        // 5. Agregar la tarjeta al contenedor
        contenedorExperiencias.addView(tarjeta, 0);

        // Limpieza de inputs
        Toast.makeText(this, "¡Experiencia agregada!", Toast.LENGTH_SHORT).show();
        etComentarios.setText("");
        btnFecha.setText("Seleccionar fecha");
    }
}