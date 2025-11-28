package modelo;

import java.awt.Color;

public class PaletaFuego {

    public static Color[] generarPaleta() {

        int numTemperaturas = 1024; // 0..1023
        Color[] paleta = new Color[numTemperaturas];

        // Colores base y porcentajes de rangos color-temperatura
        final float[] stops = {0.00f, 0.30f, 0.40f, 0.45f, 0.50f, 0.53f, 1.00f};
        final Color[] colors = {
                new Color(0, 0, 0, 0),
                new Color(39, 39, 39, 0),
                new Color(67, 10, 3, 60),
                new Color(255, 120, 20, 180),
                new Color(255, 200, 40, 210),
                new Color(255, 240, 180, 235),
                new Color(220, 230, 255, 140)
        };

        for (int i = 0; i < numTemperaturas; i++) {

            float t = i / (float) (numTemperaturas - 1);

            int idx = 0;
            while (idx < stops.length - 1 && t > stops[idx + 1])
                idx++;

            float t0 = stops[idx];
            float t1 = stops[idx + 1];

            float local = (t - t0) / (t1 - t0);

            //Suavizado de la transición
            float s = local * local * (3 - 2 * local);

            Color c0 = colors[idx];
            Color c1 = colors[idx + 1];

            // Cálculo del gradiente
            int r = (int) (c0.getRed()   + (c1.getRed()   - c0.getRed())   * s);
            int g = (int) (c0.getGreen() + (c1.getGreen() - c0.getGreen()) * s);
            int b = (int) (c0.getBlue()  + (c1.getBlue()  - c0.getBlue())  * s);
            int a = (int) (c0.getAlpha() + (c1.getAlpha() - c0.getAlpha()) * s);

            paleta[i] = new Color(r, g, b, a);
        }

        return paleta;
    }
}
