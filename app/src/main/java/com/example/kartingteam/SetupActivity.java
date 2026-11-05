package com.example.kartingteam;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class SetupActivity extends AppCompatActivity {

    private RadioGroup rgSetupNumero;
    private EditText etCorona, etPresionFrio, etPresionCaliente;
    private EditText etNeumaticos, etAlturaEje, etNotasSetup;
    private CheckBox cbTensores;
    private LinearLayout contenedorSetups;
    private String nombrePiloto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        // Binding de vistas
        Button btnVolver = findViewById(R.id.btnVolverSetup);
        Button btnGuardar = findViewById(R.id.btnGuardarSetup);
        rgSetupNumero = findViewById(R.id.rgSetupNumero);
        etCorona = findViewById(R.id.etCorona);
        etPresionFrio = findViewById(R.id.etPresionFrio);
        etPresionCaliente = findViewById(R.id.etPresionCaliente);
        etNeumaticos = findViewById(R.id.etNeumaticos);
        etAlturaEje = findViewById(R.id.etAlturaEje);
        etNotasSetup = findViewById(R.id.etNotasSetup);
        cbTensores = findViewById(R.id.cbTensores);
        contenedorSetups = findViewById(R.id.contenedorSetups);

        // Dato recibido desde MainActivity (pasaje de datos entre activities)
        nombrePiloto = getIntent().getStringExtra("nombrePiloto");

        // Evento: volver
        btnVolver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Evento: guardar setup (crea una tarjeta dinámica)
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarSetup();
            }
        });
    }

    private void guardarSetup() {
        String corona = etCorona.getText().toString().trim();
        String neumaticos = etNeumaticos.getText().toString().trim();

        // Validación: corona y neumáticos son obligatorios
        if (corona.isEmpty() || neumaticos.isEmpty()) {
            Toast.makeText(this, R.string.error_setup_vacio, Toast.LENGTH_SHORT).show();
            return;
        }

        // Qué setup (1 a 4) está seleccionado
        RadioButton rbSeleccionado = findViewById(rgSetupNumero.getCheckedRadioButtonId());
        String numeroSetup = rbSeleccionado.getText().toString();

        // Armado de la tarjeta
        int padding = getResources().getDimensionPixelSize(R.dimen.padding_card);
        int margen = getResources().getDimensionPixelSize(R.dimen.margin_small);

        LinearLayout tarjeta = new LinearLayout(this);
        tarjeta.setOrientation(LinearLayout.VERTICAL);
        tarjeta.setBackgroundResource(R.drawable.bg_card);
        tarjeta.setPadding(padding, padding, padding, padding);

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(0, 0, 0, margen);
        tarjeta.setLayoutParams(params);

        // Encabezado: "Setup usado 2"
        tarjeta.addView(crearTexto(
                getString(R.string.label_setup_numero) + " " + numeroSetup,
                R.color.racing_red, 14, true));

        // Detalles
        tarjeta.addView(crearTexto(
                getString(R.string.label_corona) + ": " + corona
                        + "  •  " + getString(R.string.label_neumaticos) + ": " + neumaticos,
                R.color.text_white, 13, false));

        tarjeta.addView(crearTexto(
                getString(R.string.label_presion_frio) + ": " + valor(etPresionFrio)
                        + "  •  " + getString(R.string.label_presion_caliente) + ": " + valor(etPresionCaliente),
                R.color.text_white, 13, false));

        String tensores = cbTensores.isChecked() ? "Sí" : "No";
        tarjeta.addView(crearTexto(
                getString(R.string.label_altura_eje) + ": " + valor(etAlturaEje)
                        + "  •  " + getString(R.string.check_tensores) + ": " + tensores,
                R.color.text_white, 13, false));

        String notas = etNotasSetup.getText().toString().trim();
        if (!notas.isEmpty()) {
            tarjeta.addView(crearTexto(notas, R.color.text_gray, 12, false));
        }

        // La más nueva arriba
        contenedorSetups.addView(tarjeta, 0);

        limpiarFormulario();
    }

    // Crea un TextView con el estilo de las tarjetas
    private TextView crearTexto(String texto, int colorRes, float sp, boolean negrita) {
        TextView tv = new TextView(this);
        tv.setText(texto);
        tv.setTextColor(ContextCompat.getColor(this, colorRes));
        tv.setTextSize(sp);
        if (negrita) {
            tv.setTypeface(null, android.graphics.Typeface.BOLD);
        }
        tv.setPadding(0, 4, 0, 4);
        return tv;
    }

    // Devuelve el texto del campo, o "-" si está vacío
    private String valor(EditText campo) {
        String t = campo.getText().toString().trim();
        return t.isEmpty() ? "-" : t;
    }

    private void limpiarFormulario() {
        etCorona.setText("");
        etPresionFrio.setText("");
        etPresionCaliente.setText("");
        etNeumaticos.setText("");
        etAlturaEje.setText("");
        etNotasSetup.setText("");
        cbTensores.setChecked(false);

        // Usa el nombre recibido desde MainActivity; si no llegó, mensaje genérico
        String mensaje = (nombrePiloto != null)
                ? "¡Setup guardado para " + nombrePiloto + "!"
                : "¡Setup guardado!";
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show();
    }
}