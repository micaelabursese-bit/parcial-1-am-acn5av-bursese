package com.example.kartingteam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;

import java.util.Locale;

// Gráfico de línea dibujado a mano con Canvas (sin librerías externas)
public class LineChartView extends View {

    private float[] valores = new float[0];
    private String[] etiquetas = new String[0];
    private String formato = "%.2f";
    private int colorLinea;
    private float density;

    private final Paint paintLinea = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintArea = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintPunto = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintGrid = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Paint paintTexto = new Paint(Paint.ANTI_ALIAS_FLAG);
    private final Path pathLinea = new Path();
    private final Path pathArea = new Path();

    public LineChartView(Context context) {
        super(context);
        init(context);
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        density = context.getResources().getDisplayMetrics().density;
        colorLinea = ContextCompat.getColor(context, R.color.racing_red);

        paintLinea.setStyle(Paint.Style.STROKE);
        paintLinea.setStrokeWidth(2.5f * density);
        paintLinea.setStrokeJoin(Paint.Join.ROUND);
        paintLinea.setStrokeCap(Paint.Cap.ROUND);

        paintArea.setStyle(Paint.Style.FILL);

        paintGrid.setStyle(Paint.Style.STROKE);
        paintGrid.setStrokeWidth(density);
        paintGrid.setColor(ContextCompat.getColor(context, R.color.border_subtle));

        paintTexto.setColor(ContextCompat.getColor(context, R.color.text_muted));
        paintTexto.setTextSize(11f * density);
        Typeface fuente = ResourcesCompat.getFont(context, R.font.barlow_medium);
        if (fuente != null) {
            paintTexto.setTypeface(fuente);
        }
    }

    // Carga los datos y vuelve a dibujar
    public void setDatos(float[] valores, String[] etiquetas, String formato, int colorLinea) {
        this.valores = valores;
        this.etiquetas = etiquetas;
        this.formato = formato;
        this.colorLinea = colorLinea;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int n = valores.length;
        if (n < 2) return;

        float izq = 48 * density;
        float der = 12 * density;
        float arriba = 12 * density;
        float abajo = 26 * density;
        float ancho = getWidth() - izq - der;
        float alto = getHeight() - arriba - abajo;

        // Rango de valores con un poco de aire arriba y abajo
        float min = valores[0];
        float max = valores[0];
        for (float v : valores) {
            min = Math.min(min, v);
            max = Math.max(max, v);
        }
        float rango = (max - min == 0) ? 1 : max - min;
        float yMin = min - rango * 0.15f;
        float yMax = max + rango * 0.15f;
        float yRango = yMax - yMin;

        // Posición de cada punto
        float[] xs = new float[n];
        float[] ys = new float[n];
        for (int i = 0; i < n; i++) {
            xs[i] = izq + i * ancho / (n - 1);
            ys[i] = arriba + (yMax - valores[i]) / yRango * alto;
        }

        // Líneas guía con su valor (máximo, medio y mínimo)
        float[] ticks = {max, (min + max) / 2f, min};
        paintTexto.setTextAlign(Paint.Align.RIGHT);
        for (float t : ticks) {
            float y = arriba + (yMax - t) / yRango * alto;
            canvas.drawLine(izq, y, izq + ancho, y, paintGrid);
            canvas.drawText(String.format(Locale.US, formato, t),
                    izq - 8 * density, y + 4 * density, paintTexto);
        }

        // Línea y área bajo la línea
        pathLinea.reset();
        pathArea.reset();
        pathLinea.moveTo(xs[0], ys[0]);
        pathArea.moveTo(xs[0], arriba + alto);
        pathArea.lineTo(xs[0], ys[0]);
        for (int i = 1; i < n; i++) {
            pathLinea.lineTo(xs[i], ys[i]);
            pathArea.lineTo(xs[i], ys[i]);
        }
        pathArea.lineTo(xs[n - 1], arriba + alto);
        pathArea.close();

        paintArea.setShader(new LinearGradient(0, arriba, 0, arriba + alto,
                ColorUtils.setAlphaComponent(colorLinea, 90),
                ColorUtils.setAlphaComponent(colorLinea, 0),
                Shader.TileMode.CLAMP));
        canvas.drawPath(pathArea, paintArea);

        paintLinea.setColor(colorLinea);
        canvas.drawPath(pathLinea, paintLinea);

        // Puntos: el último va resaltado con un aro
        paintPunto.setStyle(Paint.Style.FILL);
        paintPunto.setColor(colorLinea);
        for (int i = 0; i < n - 1; i++) {
            canvas.drawCircle(xs[i], ys[i], 3 * density, paintPunto);
        }
        paintPunto.setColor(0xFFFFFFFF);
        canvas.drawCircle(xs[n - 1], ys[n - 1], 5 * density, paintPunto);
        paintPunto.setStyle(Paint.Style.STROKE);
        paintPunto.setStrokeWidth(2 * density);
        paintPunto.setColor(colorLinea);
        canvas.drawCircle(xs[n - 1], ys[n - 1], 5 * density, paintPunto);

        // Etiquetas del eje X (V1, V2, ...)
        paintTexto.setTextAlign(Paint.Align.CENTER);
        for (int i = 0; i < n && i < etiquetas.length; i++) {
            canvas.drawText(etiquetas[i], xs[i], getHeight() - 6 * density, paintTexto);
        }
    }
}